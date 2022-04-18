package com.games.imdb.config;

import org.springframework.context.annotation.Bean;

import feign.Logger;

public class FeignBasicConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}