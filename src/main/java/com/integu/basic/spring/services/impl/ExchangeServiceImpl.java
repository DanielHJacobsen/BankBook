package com.integu.basic.spring.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
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
import static com.integu.basic.spring.validations.ValidationMessages.INVALID_CURRENCY_SHORTNAME;

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
    public Mono<ResultObj<CurrencyDto>> fetchCurrencyData(String currencyShortName) {
        CurrencyDto.Currencies currency;
        try {
            currency = CurrencyDto.Currencies.valueOf(currencyShortName);
        } catch (IllegalArgumentException e) {
            String msg = String.format(INVALID_CURRENCY_SHORTNAME, currencyShortName);
            return Mono.just(new ResultObj<>(ResultObj.Result.VALIDATION, null, new Validation("currency", msg)));
        }

        Mono<String> monoResult = client.getDataFromExternalApi();

        return monoResult
                .map(this.util::readAsJson)
                .map(jsonObj -> new ResultObj<>(ResultObj.Result.SUCCESS, createCurrencyDto(currency, jsonObj), null))
                .defaultIfEmpty(new ResultObj<>(ResultObj.Result.ERROR, null, new Validation("currency", DEFAULT_SERVER_ERROR)));
    }

    private static CurrencyDto createCurrencyDto(CurrencyDto.Currencies currency, JsonNode jsonObj) {
        double conversionRates = jsonObj.get("conversion_rates").get(currency.name()).asDouble();
        return new CurrencyDto(currency, conversionRates, 100.0d, 100 * conversionRates);
    }
}
