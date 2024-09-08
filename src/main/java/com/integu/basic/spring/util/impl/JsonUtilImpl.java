package com.integu.basic.spring.util.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.integu.basic.spring.api.ResultObj;
import com.integu.basic.spring.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import static com.integu.basic.spring.validations.ValidationMessages.DEFAULT_SERVER_ERROR;
import static com.integu.basic.spring.validations.ValidationMessages.FALLBACK_JSON_ERROR;

@Service
public class JsonUtilImpl implements JsonUtil {
    private static final Logger logger = Logger.getLogger(JsonUtilImpl.class.getName());

    private final ObjectWriter jsonWriter;
    private final ObjectMapper mapper;

    private JsonUtilImpl() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
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

    @Override
    public JsonNode readAsJson(String body) {
        try {
            return mapper.readTree(body);
        }
        catch (JsonProcessingException e) {
            logger.error(DEFAULT_SERVER_ERROR, e);
            return  mapper.createObjectNode();
        }
    }
}
