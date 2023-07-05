package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * JSON Utility class, contains utility methods for JSON objects.
 */
public final class JSONUtils {
    private JSONUtils() {
    }

    /**
     * Checks the validity of a JSON String.
     *
     * @param jsonInString The JSON String that needs to be validated.
     * @return True if the JSON String is valid, false if it's invalid. Usually throws a {@link exceptions.InvalidJSONException} in practise.
     */
    public static boolean isJSONValid(String jsonInString) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonInString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
