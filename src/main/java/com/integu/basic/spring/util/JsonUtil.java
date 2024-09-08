package com.integu.basic.spring.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.integu.basic.spring.api.ResultObj;

public interface JsonUtil {

    String parseResponseToJson(ResultObj<?> resultObj);

    JsonNode readAsJson(String body);
}
