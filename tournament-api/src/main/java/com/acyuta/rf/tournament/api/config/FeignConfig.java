package com.acyuta.rf.tournament.api.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Autowired
    RapidTennisApi rapidTennisApi;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("x-rapidapi-key", rapidTennisApi.getXRapidapiKey());
        };
    }
}