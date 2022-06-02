package io.github.juniqlim.apicall.http;

public class OnlyStringResponseBodyParser implements ResponseBodyParser {
    @Override
    public <T> T parse(String result, Class<? extends T> T) {
        if (!T.isInstance(String.class)) {
            throw new RuntimeException("json parse error: ObjectMapper is null");
        }
        return (T) result;
    }
}
