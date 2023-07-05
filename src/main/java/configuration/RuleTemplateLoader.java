package configuration;

import constants.Constants;
import exceptions.EmptyDirectoryException;
import models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.RuleTemplateSerializer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Class used to load the rule templates into memory.
 */
public final class RuleTemplateLoader {

    public static HashMap<String, List<RuleTemplate>> ruleTemplates;
    private static ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final Logger logger = LogManager.getLogger();

    private RuleTemplateLoader() {
    }

    /**
     * Loads all the rule templates into memory including the signature ID they belong to.
     * The result is stored in {@link RuleTemplateLoader#ruleTemplates}.
     */
    static void loadRuleTemplates() {
        // Lock the variable
        lock.readLock().lock();
        File ruleTemplateFolder = new File(ConfigurationLoader.configuration.getTemplateLocation());
        ruleTemplates = new HashMap<>();

        // Check if the folder exists, if not create it.
        if (ruleTemplateFolder.exists()) {
            // Get all files from folder.
            File[] templateFiles = ruleTemplateFolder.listFiles();
            try {
                // If there are any files, read them into the HashMap.
                if (templateFiles != null && templateFiles.length > 0) {

                    for (File ruleTemplate : templateFiles) {
                        String keyName = ruleTemplate.getName().substring(0, ruleTemplate.getName().indexOf("."));
                        ruleTemplates.put(keyName, RuleTemplateSerializer.serializeRuleTemplate(new String(Files.readAllBytes(ruleTemplate.toPath()))));
                    }

                } else {
                    throw new EmptyDirectoryException();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EmptyDirectoryException e) {
                logger.error("The template directory is empty. Rules will have to be configured manually.");
            } finally {

                if (!checkRuleValidity()) {
                    logger.error("An error was found in the rule template configuration. Make sure that the values for each type belong to each other.");
                    logger.error("Exiting.");
                    System.exit(1);
                } else {
                    logger.info(String.format("%d rule templates loaded into memory.", ruleTemplates.size()));
                }

                lock.readLock().unlock();
            }
        } else {
            createDefaults(ruleTemplateFolder);
            lock.readLock().unlock();
        }
    }

    /**
     * Creates the default settings for {@link RuleTemplateLoader}.
     *
     * @param folder The location of the default configuration.
     */
    private static void createDefaults(File folder) {
        logger.warn("Template folder doesn't exist. Creating it at " + ConfigurationLoader.configuration.getTemplateLocation());
        if (!folder.mkdir()) {
            logger.error("Something went wrong while creating the template folder.");
        }
    }

    /**
     * Method that checks if the given parameters in the rule template would actually work when the program is running.
     *
     * @return True or false depending on if the configuration is valid. True if its valid, false if it isn't.
     */
    private static boolean checkRuleValidity() {
        boolean validity = true;

        // Loop through all the RuleTemplates
        for (Map.Entry<String, List<RuleTemplate>> entry : ruleTemplates.entrySet()) {
            for (RuleTemplate ruleTemplate : entry.getValue()) {

                // Check the parameters
                for (RuleParameter parameter : ruleTemplate.getParameters()) {

                    switch (parameter.getType().toUpperCase()) {
                        case "EVENT":
                            try {
                                Event.class.getMethod("get" + Constants.JSON_JAVA_VARIABLE_NAMES.get(parameter.getValue()));
                            } catch (NoSuchMethodException e) {
                                validity = false;
                            }

                            break;
                        case "ALERT":
                            try {
                                Alert.class.getMethod("get" + Constants.JSON_JAVA_VARIABLE_NAMES.get(parameter.getValue()));
                            } catch (NoSuchMethodException e) {
                                validity = false;
                            }

                            break;
                        case "HTTP":
                            try {
                                Http.class.getMethod("get" + Constants.JSON_JAVA_VARIABLE_NAMES.get(parameter.getValue()));
                            } catch (NoSuchMethodException e) {
                                validity = false;
                            }

                            break;
                    }
                }
            }
        }

        return validity;

    }
}
