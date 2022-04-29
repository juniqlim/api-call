package io.github.juniq.apicall.http;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * interface
 */
public class ResponseBodyParser {
    private final ObjectMapper objectMapper;

    private ResponseBodyParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public static ResponseBodyParser of(ObjectMapper objectMapper) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return new ResponseBodyParser(objectMapper);
    }

    public <T> T parse(String result, Class<? extends T> T) {
        try {
            return objectMapper.readValue(result, T);
        } catch (IOException e) {
            throw new RuntimeException("json parse error: " + e.getMessage());
        }
    }
}
