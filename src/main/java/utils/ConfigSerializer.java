package utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Configuration;

import java.io.IOException;

/**
 * Contains the method to serialize the {@link Configuration} model.
 */
public class ConfigSerializer {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * converts the JSON String to a {@link Configuration} object.
     *
     * @param json The JSON String read from the configuration file (or elsewhere).
     * @return A {@link Configuration} object with values from the JSON String.
     * @throws IOException If anything goes wrong, this exception is thrown. Mostly due to invalid JSON which is caught using {@link exceptions.InvalidJSONException}.
     */
    public static Configuration serializeConfiguration(String json) throws IOException {
        return OBJECT_MAPPER.readValue(json, Configuration.class);
    }
}
