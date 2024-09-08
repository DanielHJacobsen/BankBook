package com.integu.basic.spring.services.impl;

import com.integu.basic.spring.api.ResultObj;
import com.integu.basic.spring.client.ExchangeClient;
import com.integu.basic.spring.dto.CurrencyDto;
import com.integu.basic.spring.services.ExchangeService;
import com.integu.basic.spring.validations.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.integu.basic.spring.validations.ValidationMessages.DEFAULT_SERVER_ERROR;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeClient client;

    @Autowired
    public ExchangeServiceImpl(ExchangeClient client) {
        this.client = client;

    }

    @Override
    public Mono<ResultObj<CurrencyDto>> fetchCurrencyData(CurrencyDto.Currencies currency) {
        Mono<String> monoResult = client.getDataFromExternalApi(currency);
        return monoResult
                .map(body -> new ResultObj<>(ResultObj.Result.SUCCESS, new CurrencyDto(currency, 2L), null))
                .defaultIfEmpty(new ResultObj<>(ResultObj.Result.ERROR, null, new Validation("currency", DEFAULT_SERVER_ERROR)));
    }
}
