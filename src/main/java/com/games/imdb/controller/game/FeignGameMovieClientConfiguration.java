package com.games.imdb.controller.game;

import org.springframework.context.annotation.Bean;
import feign.auth.BasicAuthRequestInterceptor;

public class FeignGameMovieClientConfiguration {

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("rocha", "123");
    }

}