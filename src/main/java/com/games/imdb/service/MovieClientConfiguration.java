package com.games.imdb.service;

import org.springframework.context.annotation.Bean;
import feign.auth.BasicAuthRequestInterceptor;

public class MovieClientConfiguration {

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("rocha", "123");
    }

}