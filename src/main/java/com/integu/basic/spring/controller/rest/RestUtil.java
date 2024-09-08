package com.integu.basic.spring.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.integu.basic.spring.api.ResultObj;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import static com.integu.basic.spring.validations.ValidationMessages.FALLBACK_JSON_ERROR;

@Service
public class RestUtil {
    private static final Logger logger = Logger.getLogger(RestUtil.class.getName());

    private final ObjectWriter jsonWriter;

    private RestUtil() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        this.jsonWriter = mapper.writer().withDefaultPrettyPrinter();
    }

    public String parseResponseToJson(ResultObj<?> resultObj) {
        try {
            return this.jsonWriter.writeValueAsString(resultObj);
        } catch (JsonProcessingException e) {
            logger.error(e);
            return FALLBACK_JSON_ERROR;
        }
    }
}
