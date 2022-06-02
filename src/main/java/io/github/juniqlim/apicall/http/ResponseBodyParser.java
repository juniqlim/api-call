package io.github.juniqlim.apicall.http;

public interface ResponseBodyParser {
    <T> T parse(String result, Class<? extends T> T);
}
