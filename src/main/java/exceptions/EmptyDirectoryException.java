package exceptions;

/**
 * Exception thrown when the directory is empty.
 */
public class EmptyDirectoryException extends Exception {

    public EmptyDirectoryException() {
    }

    public EmptyDirectoryException(String message) {
        super(message);
    }
}
