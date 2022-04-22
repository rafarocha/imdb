package com.games.imdb.service.client;

import com.games.imdb.config.FeignBasicConfig;
import com.games.imdb.domain.Game;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${app.host.url}", value = "auto", configuration = FeignBasicConfig.class)
public interface GameClient {

    @PutMapping("/games/{id}")
    String vote(@PathVariable Long id, @RequestParam int step, @RequestParam int vote);

    @GetMapping("/games/{id}/resume")
    String resume(@PathVariable Long id, @RequestParam int step, @RequestParam int vote);

    @GetMapping("game/{id}/step/{step}")
    String step(@PathVariable Long id, @PathVariable int step);

    @GetMapping("/{id}")
    String get(@PathVariable Long id);

    @PostMapping("/games")
    Game create();
    
}