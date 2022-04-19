package com.games.imdb.service.client;

import com.games.imdb.config.FeignBasicConfig;
import com.games.imdb.service.IMDBHolderFallback;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${app.imdb.url}", value = "imdb", configuration = FeignBasicConfig.class, fallback = IMDBHolderFallback.class)
public interface IMDBClient {

    // https://www.omdbapi.com/?t=john
    @GetMapping("/")
    String movies(@RequestParam String t, @RequestParam String apikey);
}
