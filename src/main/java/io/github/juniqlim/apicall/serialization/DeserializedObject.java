package io.github.juniqlim.apicall.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeserializedObject<T> {
    private final ObjectMapper objectMapper;
    private final String json;
    private final Class<T> clazz;

    public DeserializedObject(String json, Class<T> clazz) {
        this(CacheObjectMapper.objectMapper(), json, clazz);
    }

    public DeserializedObject(ObjectMapper objectMapper, String json, Class<T> clazz) {
        this.objectMapper = objectMapper;
        this.json = json;
        this.clazz = clazz;
    }

    public T object() throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }
}
