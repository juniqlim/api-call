package im.juniq.apicall.http;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

import im.juniq.apicall.http.HttpLogging.SystemOutPrintHttpLogging;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

class HttpLoggingTest {
    @Test
    void log() {
        HttpLogging logging = new SystemOutPrintHttpLogging();
        Request request = Request.of("male", "juniq", "juniq@juniq.com", "active");
        HttpResponse response = HttpResponse.of(HttpStatus.OK, "{\"code\": \"success\"}");
        HttpMessage httpMessage = HttpMessage.of(HttpMethod.POST, "https://gorest.co.in/public/v2/users", header(), request, response);

        assertThatCode(() -> logging.log(httpMessage)).doesNotThrowAnyException();
    }

    @Test
    void errorLog() {
        HttpLogging logging = new SystemOutPrintHttpLogging();
        Request request = Request.of("male", "juniq", "juniq@juniq.com", "active");
        HttpResponse response = HttpResponse.of(HttpStatus.OK, "{\"code\": \"success\"}");
        HttpMessage httpMessage = HttpMessage.of(HttpMethod.POST, "https://gorest.co.in/public/v2/users", header(), request, response);

        assertThatCode(() -> logging.errorLog(httpMessage)).doesNotThrowAnyException();
    }

    private Map<String, String> header() {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer d6d64785492c26bf654f88db031777b7802821ca14e4ee62acd12acc29470d1f");
        return map;
    }
}