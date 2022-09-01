package io.github.juniqlim.apicall.http.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.juniqlim.apicall.http.HttpApiCallException;
import io.github.juniqlim.apicall.http.HttpApiCallResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * interface
 */
public interface HttpLogging {
    void log(HttpApiCallResult httpApiCallResult);
    void infoLog(HttpApiCallResult httpApiCallResult);
    void errorLog(HttpApiCallResult httpApiCallResult);

    class SystemOutPrintHttpLogging implements HttpLogging {
        private final Logger log = LoggerFactory.getLogger(this.getClass());
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void log(HttpApiCallResult httpApiCallResult) {
            if (httpApiCallResult.response().isError()) {
                errorLog(httpApiCallResult);
                throw new HttpApiCallException(httpApiCallResult.response(),
                    "Http request call exception - status: " + httpApiCallResult.response().httpStatus() + ", response: "
                        + httpApiCallResult.response().body());
            }
            infoLog(httpApiCallResult);
        }

        @Override
        public void infoLog(HttpApiCallResult httpApiCallResult) {
            log.info("status:{},method:{},url:{},header:{},request:{},response:{}", httpApiCallResult.response().httpStatus(),
                httpApiCallResult.httpMethod().name(), httpApiCallResult.url(), httpApiCallResult.header().toString(),
                stringOf(httpApiCallResult.request()), httpApiCallResult.response().body());
        }

        @Override
        public void errorLog(HttpApiCallResult httpApiCallResult) {
            log.error("status:{},method:{},url:{},header:{},request:{},response:{}", httpApiCallResult.response().httpStatus(),
                httpApiCallResult.httpMethod().name(), httpApiCallResult.url(), httpApiCallResult.header().toString(),
                stringOf(httpApiCallResult.request()), httpApiCallResult.response().body());
        }

        private String stringOf(Object request) {
            try {
                return objectMapper.writeValueAsString(request);
            } catch (JsonProcessingException e) {
                return "";
            }
        }
    }
}
