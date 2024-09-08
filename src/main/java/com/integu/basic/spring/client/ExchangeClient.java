package com.integu.basic.spring.client;

import reactor.core.publisher.Mono;

public interface ExchangeClient {
    Mono<String> getDataFromExternalApi();
}
