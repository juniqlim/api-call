package io.github.juniqlim.apicall.http;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * interface
 */
public class ObjectMapperResponseBodyParser implements ResponseBodyParser {
    private final ObjectMapper objectMapper;

    public ObjectMapperResponseBodyParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T parse(String result, Class<? extends T> T) {
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(result, T);
        } catch (IOException e) {
            throw new RuntimeException("json parse error: " + e.getMessage());
        }
    }
}
