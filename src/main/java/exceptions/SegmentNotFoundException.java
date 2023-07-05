package exceptions;

/**
 * Exception thrown when a code segment thats used in the configuration file isn't found in the class list.
 */
public class SegmentNotFoundException extends Exception {

    public SegmentNotFoundException() {
    }

    public SegmentNotFoundException(String message) {
        super(message);
    }
}
