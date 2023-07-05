package templates;

import models.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The class to be implemented for the seperate code segment classes.
 */
public interface CodeSegment {

    Logger logger = LogManager.getLogger();


    /**
     * The method that is ran when the class is used elsewhere in the application.
     * If there are multiple other methods, call them inside this method so that they will be executed.
     *
     * @param event The event that is received from LogStash and serialized. See {@link Event}.
     * @return True or false depending on the output of the written code segment.
     */
    boolean start(Event event);

}
