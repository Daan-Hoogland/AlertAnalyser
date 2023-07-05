package configuration;

import exceptions.SegmentNotFoundException;
import models.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Class that controls all the configuration related things.
 */
public class ConfigurationControl {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final Logger logger = LogManager.getLogger();

    /**
     * Start the configuration sequence of the application.
     * It begins by loading in the configuration settings, after which it starts a timer which refreshes every x hours.
     * <p>
     * The methods executed here to read in configuration are:
     * {@link ConfigurationLoader#loadInitialConfiguration()}
     * {@link RuleTemplateLoader#loadRuleTemplates()}
     * {@link ClassLoader#loadClasses()}
     * {@link #checkIfConfigIsValid()}
     * {@link ClassLoader#compileClasses()}
     * <p>
     * The methods used in the scheduled executorservice are:
     * {@link ConfigurationLoader#loadConfiguration()}
     * {@link RuleTemplateLoader#loadRuleTemplates()}
     * {@link ClassLoader#loadClasses()}
     * {@link #checkIfConfigIsValid()}
     * {@link ClassLoader#compileClasses()}
     */
    public void startConfiguration() {

        // Load everything
        ConfigurationLoader.loadInitialConfiguration();
        RuleTemplateLoader.loadRuleTemplates();
        ClassLoader.loadClasses();

        // Check if the loaded configuration is valid
        checkIfConfigIsValid();

        // Compile all classes
        ClassLoader.compileClasses();


        // Create a runnable that reloads the configuration (does the same as above)
        final Runnable updater = () -> {
            ConfigurationLoader.loadConfiguration();
            RuleTemplateLoader.loadRuleTemplates();
            ClassLoader.loadClasses();
            checkIfConfigIsValid();
            ClassLoader.compileClasses();
        };

        // Make the application reload the settings every x hours.
        scheduler.scheduleAtFixedRate(updater, ConfigurationLoader.configuration.getReloadInterval(), ConfigurationLoader.configuration.getReloadInterval(), TimeUnit.HOURS);
    }

    /**
     * Method that calls {@link #configValidation()} in order to check validity of config.
     * Shows message or does nothing depending on output of {@link #configValidation()}.
     */
    private void checkIfConfigIsValid() {
        try {
            if (!configValidation()) {
                throw new SegmentNotFoundException();
            }
        } catch (SegmentNotFoundException e) {
            logger.fatal("Module used in configuration file cannot be found in classes folder.");
            logger.fatal("Application exiting.");
            System.exit(1);
        }
    }

    /**
     * Checks if the configation file is valid.
     * Will look through all the used classes in the configuration file and see if they exist within the application.
     *
     * @return true if the config file is valid and all classes are found ready to be compiled, or false if not all of them can be found.
     */
    private boolean configValidation() {
        Configuration config = ConfigurationLoader.configuration;
        HashMap<String, String> loadedClasses = ClassLoader.loadedClasses;

        // Check if the entries used in the configuration are actual classes loaded in by the application.
        for (Map.Entry<String, List<String>> entry : config.getSignatureConfigurations().entrySet()) {
            for (String module : entry.getValue()) {
                if (!loadedClasses.containsKey(module)) {
                    return false;
                }
            }
        }

        return true;
    }
}
