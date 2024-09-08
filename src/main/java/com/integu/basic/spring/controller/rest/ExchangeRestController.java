package com.integu.basic.spring.controller.rest;

import com.integu.basic.spring.dto.CurrencyDto;
import com.integu.basic.spring.services.ExchangeService;
import com.integu.basic.spring.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ExchangeRestController {
    private static final Logger logger = Logger.getLogger(ExchangeRestController.class.getName());
    private final ExchangeService exchangeService;
    private final JsonUtil util;

    @Autowired
    public ExchangeRestController(ExchangeService exchangeService, JsonUtil util) {
        this.exchangeService = exchangeService;
        this.util = util;
    }

    @GetMapping("/exchange/{accountId}/json")
    public Mono<String> getAccount(@PathVariable("accountId") String accountId) {
        CurrencyDto.Currencies currency;
        try {
            currency = CurrencyDto.Currencies.valueOf(accountId);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid currency shortname provided. Defaulting to DKK.");
            currency = CurrencyDto.Currencies.DKK;
        }

        return exchangeService.fetchCurrencyData(currency).map(util::parseResponseToJson);
    }
}
