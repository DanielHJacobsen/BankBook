package com.integu.basic.spring.client.impl;

import com.integu.basic.spring.client.ExchangeClient;
import com.integu.basic.spring.dto.CurrencyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ExchangeClientImpl implements ExchangeClient {

        private final WebClient webClient;

        @Autowired
        public ExchangeClientImpl(WebClient.Builder webClientBuilder) {
                String apiKey = System.getenv("API_KEY");
                this.webClient = webClientBuilder.baseUrl("https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/").build();
        }

        @Override
        public Mono<String> getDataFromExternalApi() {
            //noinspection StringTemplateMigration
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/" + CurrencyDto.Currencies.DKK.name()).build())
                    .retrieve()
                    .bodyToMono(String.class);  // `MyResponse` is your DTO class for the API response
        }

}
