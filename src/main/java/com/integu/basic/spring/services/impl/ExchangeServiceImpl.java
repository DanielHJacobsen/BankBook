package com.integu.basic.spring.services.impl;

import com.integu.basic.spring.api.ResultObj;
import com.integu.basic.spring.client.ExchangeClient;
import com.integu.basic.spring.controller.rest.ExchangeRestController;
import com.integu.basic.spring.dto.CurrencyDto;
import com.integu.basic.spring.services.ExchangeService;
import com.integu.basic.spring.util.JsonUtil;
import com.integu.basic.spring.validations.Validation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.integu.basic.spring.validations.ValidationMessages.DEFAULT_SERVER_ERROR;

@Service
public class ExchangeServiceImpl implements ExchangeService {
    private static final Logger logger = Logger.getLogger(ExchangeRestController.class.getName());

    private final ExchangeClient client;
    private final JsonUtil util;

    @Autowired
    public ExchangeServiceImpl(ExchangeClient client, JsonUtil util) {
        this.client = client;

        this.util = util;
    }

    @Override
    public Mono<ResultObj<CurrencyDto>> fetchCurrencyData(CurrencyDto.Currencies currency) {
        Mono<String> monoResult = client.getDataFromExternalApi();

        // TODO - Rounding issue.

        return monoResult
                .map(this.util::readAsJson)
                .map(jsonObj -> new ResultObj<>(ResultObj.Result.SUCCESS, new CurrencyDto(currency, jsonObj.get("conversion_rates").get(currency.name()).asLong()), null))
                .defaultIfEmpty(new ResultObj<>(ResultObj.Result.ERROR, null, new Validation("currency", DEFAULT_SERVER_ERROR)));
    }
}
