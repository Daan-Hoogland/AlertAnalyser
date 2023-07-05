package configuration;

import exceptions.EmptyDirectoryException;
import net.openhft.compiler.CompilerUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import templates.CodeSegment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Class used in order to load classes into memory and compile them ready for use.
 */
public class ClassLoader {
    public static HashMap<String, CodeSegment> compiledClasses;
    static HashMap<String, String> loadedClasses;
    private static final Logger logger = LogManager.getLogger();

    private static ReadWriteLock lock = new ReentrantReadWriteLock();

    private ClassLoader() {
    }

    /**
     * Loads all classes from a given filepath (which is configured using the configuration file).
     * into memory. Doing this allows us to compile the given files to be used later in the application.
     * The result is stored in {@link ClassLoader#loadClasses()}.
     */
    static void loadClasses() {
        // Lock the variable
        lock.readLock().lock();
        File classFolder = new File(ConfigurationLoader.configuration.getClassLocation());
        loadedClasses = new HashMap<>();

        // Check if the folder exists, if not create it.
        if (classFolder.exists()) {

            // Get all files from folder
            File[] classFiles = classFolder.listFiles();
            try {
                // Read all files into a HashMap. Remove the .java at the end to get the actual name of the file.
                // This is how it should be written in the configuration file.
                if (classFiles != null && classFiles.length > 0) {
                    for (File classFile : classFiles) {
                        String keyName = classFile.getName().substring(0, classFile.getName().indexOf("."));
                        loadedClasses.put(keyName, new String(Files.readAllBytes(classFile.toPath())));
                    }
                } else {
                    // Throw exception when directory is empty.
                    throw new EmptyDirectoryException();
                }
            } catch (IOException e) {
                logger.error("Something went wrong while reading the classes folder.", e);
            } catch (EmptyDirectoryException e) {
                logger.fatal("Application cannot start without any code segments to check validity of alert.");
                logger.fatal("Exiting.");
                System.exit(1);
            } finally {
                // Unlock when done
                logger.info(String.format("%d classes loaded into memory correctly.", loadedClasses.size()));
                lock.readLock().unlock();
            }
        } else {
            createDefaults(classFolder);

            lock.readLock().unlock();
        }
    }

    /**
     * Creates the default settings for {@link ClassLoader}.
     *
     * @param folder The location of the default configuration.
     */
    private static void createDefaults(File folder) {
        // Create the folder without any files in it to allow user to add files.
        logger.warn("Class folder doesnt exist. Creating it at " + ConfigurationLoader.configuration.getClassLocation());
        if (!folder.mkdir()) {
            logger.error("Something went wrong while creating the template folder.");
        }
    }

    /**
     * Compiles all classes previously parsed by {@link ClassLoader#loadClasses()} using the Java-Runtime-Compiler library.
     * All classes should implement the CodeSegment interface {@link CodeSegment}.
     * The compiled classes are stored in {@link ClassLoader#compiledClasses}.
     */
    static void compileClasses() {
        // Lock the variable
        lock.readLock().lock();
        compiledClasses = new HashMap<>();

        // For every parsed .java file, compile them into a CodeSegment object.
        loadedClasses.forEach((key, val) -> {
            try {
                // Compile and save class.
                Class aClass = CompilerUtils.CACHED_COMPILER.loadFromJava(key, val);
                compiledClasses.put(key, (CodeSegment) aClass.newInstance());
            } catch (ClassNotFoundException | IllegalAccessException e) {
                logger.error("Exception thrown: ", e);
            } catch (InstantiationException e) {
                logger.fatal("Make sure your classes all implement CodeSegment and the start(Event) method as seen in documentation.");
                logger.fatal("Exiting.");
                System.exit(1);
            }
        });
        // Unlock when done.
        logger.info(String.format("%d classes compiled and loaded into memory.", compiledClasses.size()));
        lock.readLock().unlock();
    }
}
