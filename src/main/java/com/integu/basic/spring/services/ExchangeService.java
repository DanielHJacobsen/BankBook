package com.integu.basic.spring.services;

import com.integu.basic.spring.api.ResultObj;
import com.integu.basic.spring.dto.CurrencyDto;
import reactor.core.publisher.Mono;

public interface ExchangeService {

    Mono<ResultObj<CurrencyDto>> fetchCurrencyData(String currency);
}
