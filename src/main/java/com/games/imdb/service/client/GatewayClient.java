package com.games.imdb.service.client;

import com.games.imdb.service.MovieClientConfiguration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "${app.host.url}", value = "gateway", configuration = MovieClientConfiguration.class)
public interface GatewayClient {

    @GetMapping("/gateway")
    String movie();

}