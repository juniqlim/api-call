package io.github.juniqlim.apicall.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializedObject {
    private final ObjectMapper objectMapper;
    private final Object object;

    public SerializedObject(Object object) {
        this(CacheObjectMapper.objectMapper(), object);
    }

    public SerializedObject(ObjectMapper objectMapper, Object object) {
        this.objectMapper = objectMapper;
        this.object = object;
    }

    public String json() throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
