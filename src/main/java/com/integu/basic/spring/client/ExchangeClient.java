package com.integu.basic.spring.client;

import com.integu.basic.spring.dto.CurrencyDto;
import reactor.core.publisher.Mono;

public interface ExchangeClient {
    Mono<String> getDataFromExternalApi(CurrencyDto.Currencies currencies);
}
