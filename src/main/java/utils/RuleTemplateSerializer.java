package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.RuleTemplate;

import java.io.IOException;
import java.util.List;

/**
 * Contains the method to serialize the {@link RuleTemplate} model.
 */
public class RuleTemplateSerializer {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * Converts the JSON String to a {@link RuleTemplate} object.
     *
     * @param json The JSON String received from LogStash (or somewhere else).
     * @return A {@link RuleTemplate} object with the values from the JSON String.
     * @throws IOException If anything goes wrong, this exception is thrown. Mostly due to invalid JSON which is caught using {@link exceptions.InvalidJSONException}.
     */
    public static List<RuleTemplate> serializeRuleTemplate(String json) throws IOException {
        return OBJECT_MAPPER.readValue(json, new TypeReference<List<RuleTemplate>>() {
        });
    }
}
