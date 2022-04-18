package com.games.imdb.controller.game;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "${app.host.url}", value = "game", configuration = FeignGameMovieClientConfiguration.class)
public interface GameMovieClient {

    @GetMapping("/movie")
    String movie();

}