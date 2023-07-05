package analysis;

import configuration.ClassLoader;
import configuration.ConfigurationLoader;
import configuration.RuleTemplateLoader;
import constants.Constants;
import models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import templates.CodeSegment;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The class that will run with every incoming event. Implements {@link Runnable}.
 */
public class EventAnalyser implements Runnable {

    private Event event;
    private static final Logger logger = LogManager.getLogger();

    public EventAnalyser(Event event) {
        this.event = event;
    }

    /**
     * Method that is called whenever an event comes in.
     * Runs the compiled classes against the values found in the event.
     */
    @Override
    public void run() {

        // Check signature ID of event
        // Get the compiled classes of that signature ID
        List<String> classNamesBySignatureId = ConfigurationLoader.configuration.getSignatureConfigurations().get(event.getAlert().getSignatureId());

        HashMap<String, CodeSegment> compiledClasses = new HashMap<>();

        classNamesBySignatureId.forEach(name -> {
            compiledClasses.put(name, ClassLoader.compiledClasses.get(name));
        });

        // Run the compiled classes for that signature ID and save them in a HashMap for potential future use.
        // (Keeps the result per compiled class instead of anonymous results, maybe useful for analyzing or extension later on)
        HashMap<String, Boolean> results = new HashMap<>();
        compiledClasses.forEach((key, val) -> results.put(key, val.start(event)));

        // Check the results of the compiled classes
        if (results.containsValue(false)) {
            // Replace placeholders with variables from event object
            List<String> rules = generateRules(RuleTemplateLoader.ruleTemplates.get(event.getAlert().getSignatureId()));

            // Do something with rule (put it in DB or print temporarily)
            rules.forEach(rule -> logger.debug(rule));
        }

    }

    /**
     * Generates a set of suppress rules based on templates found in the configuration folder.
     *
     * @param ruleTemplates The list of loaded {@link RuleTemplate}.
     * @return A list of rules containing values from the event.
     */
    private List<String> generateRules(List<RuleTemplate> ruleTemplates) {
        ArrayList<String> rules = new ArrayList<>();

        ruleTemplates.forEach(ruleTemplate -> {
            String rule = ruleTemplate.getRule();
            for (RuleParameter parameter : ruleTemplate.getParameters()) {
                switch (parameter.getType().toUpperCase()) {
                    case "EVENT":
                        try {
                            // Replace the placeholder by dynamically calling the getter method for the variable
                            rule = rule.replace("[" + parameter.getValue() + "]",
                                    // Get the getter method for the variable which resides in the Event class.
                                    event.getClass().getMethod("get" + Constants.JSON_JAVA_VARIABLE_NAMES.get(parameter.getValue()))
                                            .invoke(event).toString());
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "ALERT":
                        try {
                            // Replace the placeholder by dynamically calling the getter method for the variable
                            rule = rule.replace("[" + parameter.getValue() + "]",
                                    // Get the getter metheod of the variable which resides in the Alert class.
                                    Alert.class.getMethod("get" + Constants.JSON_JAVA_VARIABLE_NAMES.get(parameter.getValue()))
                                            // Here we call the getAlert method on the Event just like on line 72, instead using a fixed String (getAlert).
                                            // This allows us to use the Alert object instead of the Event object.
                                            .invoke(event.getClass().getMethod("getAlert").invoke(event)).toString()
                            );
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            e.printStackTrace();
                        }

                        break;
                    case "HTTP":
                        try {
                            // Replace the placeholder by dynamically calling the getter method for the variable
                            rule = rule.replace("[" + parameter.getValue() + "]",
                                    // Get the getter metheod of the variable which resides in the Http class.
                                    Http.class.getMethod("get" + Constants.JSON_JAVA_VARIABLE_NAMES.get(parameter.getValue()))
                                            // Here we call the getHttp method on the Event just like on line 72, instead using a fixed String (getHttp).
                                            // This allows us to use the Http object instead of the Event object.
                                            .invoke(event.getClass().getMethod("getHttp").invoke(event)).toString()
                            );
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
            // Add the rule to a list of rules.
            rules.add(rule);
        });

        return rules;
    }
}