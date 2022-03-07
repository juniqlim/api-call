package im.juniq.apicall.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface HttpLogging {
    void log(HttpMessage httpMessage);
    void errorLog(HttpMessage httpMessage);

    class SystemOutPrintHttpLogging implements HttpLogging {
        private final Logger log = LoggerFactory.getLogger(this.getClass());
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void log(HttpMessage httpMessage) {
            log.info("status:{},method:{},url:{},header:{},req:{},res:{}", httpMessage.response().httpStatus(),
                httpMessage.httpMethod().name(), httpMessage.url(), httpMessage.header().toString(),
                stringOf(httpMessage.request()), httpMessage.response().body());
        }

        @Override
        public void errorLog(HttpMessage httpMessage) {
            log.error("status:{},method:{},url:{},header:{},req:{},res:{}", httpMessage.response().httpStatus(),
                httpMessage.httpMethod().name(), httpMessage.url(), httpMessage.header().toString(),
                stringOf(httpMessage.request()), httpMessage.response().body());
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
