package com.games.imdb.config;

import org.springframework.context.annotation.Bean;

import feign.auth.BasicAuthRequestInterceptor;

public class FeignBasicConfig {

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("system", "123");
    }

}