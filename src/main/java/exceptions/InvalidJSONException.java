package exceptions;

/**
 * Exception thrown when JSON is invalid.
 */
public class InvalidJSONException extends Exception {

    public InvalidJSONException() {
    }

    public InvalidJSONException(String message) {
        super(message);
    }

}
