package configuration;

import constants.Constants;
import exceptions.InvalidJSONException;
import models.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConfigSerializer;
import utils.JSONUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Class used to load the general configuration into memory.
 * Without the general configuration, the other configuration classes don't know where to look.
 */
public final class ConfigurationLoader {

    public static Configuration configuration;
    private static ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final Logger logger = LogManager.getLogger();

    private ConfigurationLoader() {
    }

    /**
     * Loads all the general configuration into memory and makes it available for other classes.
     * The result is stored in {@link ConfigurationLoader#configuration}.
     */
    static void loadInitialConfiguration() {
        // Lock the variable
        lock.readLock().lock();
        File configurationPath = new File(Constants.CONFIG_FOLDER);
        File configurationFile = new File(Constants.CONFIG_FOLDER + Constants.CONFIG_FILE);

        try {
            // Check if the configuration path and file exist, otherwise create them.
            if (configurationPath.exists() && configurationFile.exists()) {

                // If file exists, read contents into String
                String configContent = new String(Files.readAllBytes(configurationFile.toPath()));

                // If JSON isn't valid, throw an InvalidJSONException, otherwise serialize into object.
                if (JSONUtils.isJSONValid(configContent)) {
                    configuration = ConfigSerializer.serializeConfiguration(configContent);
                    logger.info("Configuration loaded.");
                } else {
                    throw new InvalidJSONException();
                }
            } else {
                // If file doesn't exist, create everything that should be there and load it into memory.
                createDefaults(configurationPath, configurationFile);
                configuration = ConfigSerializer.serializeConfiguration(Constants.CONFIG_TEMPLATE);

            }

        } catch (FileNotFoundException e) {
            logger.error("No configuration file was found.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidJSONException e) {
            logger.fatal("The JSON in the configuration file is invalid.");
            logger.fatal("Use a tool like https://jsonlint.com/ to correct it and restart the application.");
            System.exit(1);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Creates the default application settings.
     *
     * @param folder The folder in which the default configuration will be created.
     * @param file   The file in which the default configuration will be written.
     * @throws IOException Thrown if anything goes wrong during the initial write of configuration settings.
     */
    private static void createDefaults(File folder, File file) throws IOException {
        // If file doesn't exist, create everything that should be there.
        logger.warn(String.format("Configuration file doesn't exist. Creating configuration folder at %s", folder));
        folder.mkdir();
        file.createNewFile();
        Files.write(Paths.get(file.toURI()), Constants.CONFIG_TEMPLATE.getBytes());
    }

    /**
     * The same as {@link ConfigurationLoader#loadInitialConfiguration()}, except doesn't include the default generation bit.
     * Should be used to refresh settings while program is running.
     * If no configuration files are found, application throws an error and exits.
     */
    static void loadConfiguration() {
        // Lock the variable
        lock.readLock().lock();
        Configuration oldConfiguration = configuration;
        File configurationPath = new File(Constants.CONFIG_FOLDER);
        File configurationFile = new File(Constants.CONFIG_FOLDER + Constants.CONFIG_FILE);

        try {
            // Load in JSON as a String.
            String configContent = new String(Files.readAllBytes(configurationFile.toPath()));

            // Validate JSON, if valid serialize it, otherwise throw InvalidJSONException and keep using the old configuration.
            if (JSONUtils.isJSONValid(configContent)) {
                configuration = ConfigSerializer.serializeConfiguration(configContent);
                logger.info("Configuration loaded.");
            } else {
                configuration = oldConfiguration;
                throw new InvalidJSONException();
            }

        } catch (FileNotFoundException e) {
            logger.fatal("No configuration files found in " + configurationPath.getAbsolutePath() + ".");
            logger.fatal("Exiting.");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidJSONException e) {
            logger.error("The JSON in the configuration file is invalid.");
            logger.error("Use a tool like https://jsonlint.com/ to correct it. Until then the previous configuration will be used.");
        } finally {
            // Unlock when done.
            lock.readLock().unlock();
        }
    }
}