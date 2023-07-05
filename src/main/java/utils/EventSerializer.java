package utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Event;

import java.io.IOException;

/**
 * Contains the method to serialize the {@link Event} model.
 */
public class EventSerializer {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * Converts the JSON String to a {@link Event} object.
     *
     * @param json The JSON String received from LogStash (or somewhere else).
     * @return A {@link Event} object with the values from the JSON String.
     * @throws IOException If anything goes wrong, this exception is thrown. Mostly due to invalid JSON which is caught using {@link exceptions.InvalidJSONException}.
     */
    public static Event serializeEvent(String json) throws IOException {
        return OBJECT_MAPPER.readValue(json, Event.class);
    }
}
