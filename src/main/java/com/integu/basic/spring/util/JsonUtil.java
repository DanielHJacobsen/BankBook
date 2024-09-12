package com.integu.basic.spring.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.integu.basic.spring.api.ResultObj;
import org.springframework.http.ResponseEntity;

public interface JsonUtil {

    ResponseEntity<String> parseResponseToJson(ResultObj<?> resultObj);

    JsonNode readAsJson(String body);
}
