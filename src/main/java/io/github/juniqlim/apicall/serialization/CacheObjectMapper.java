package io.github.juniqlim.apicall.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class CacheObjectMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper objectMapper() {
        return objectMapper;
    }
}
