package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class GenericSerializer<T> {

    private Class<T> parameterClass;

    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * Pass the Class of the object.
     *
     * @param parameterClass The class of the object.
     */
    public GenericSerializer(Class<T> parameterClass) {
        this.parameterClass = parameterClass;
    }

    /**
     * Converts the JSON String to an object.
     *
     * @param json The JSON String received from somewhere.
     * @return An Object of type T with the values from the JSON String.
     * @throws IOException If anything goes wrong, this exception is thrown. Mostly due to invalid JSON which is caught using {@link exceptions.InvalidJSONException}.
     */
    public T serializeObject(String json) throws IOException {
        return OBJECT_MAPPER.readValue(json, parameterClass);
    }

    /**
     * Converts the JSON String into a list of objects.
     *
     * @param json The JSON String received from somewhere.
     * @return An Object of type T with the values from the JSON String.
     * @throws IOException If anything goes wrong, this exception is thrown. Mostly due to invalid JSON which is caught using {@link exceptions.InvalidJSONException}.
     */
    public List<T> serializeObjectList(String json) throws IOException {
        return OBJECT_MAPPER.readValue(json, new TypeReference<List<T>>() {
        });
    }
}
