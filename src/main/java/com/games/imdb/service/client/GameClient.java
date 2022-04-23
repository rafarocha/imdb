package com.games.imdb.service.client;

import java.util.Map;

import com.games.imdb.domain.RankUser;
import com.games.imdb.domain.to.PainelGame;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${app.host.url}", value = "game")
public interface GameClient {

    @PutMapping("/games/{id}")
    PainelGame vote(@RequestHeader Map<String, String> headers, @PathVariable Long id, @RequestParam int vote);

    @PutMapping("/games/{id}/last")
    RankUser lastVote(@RequestHeader Map<String, String> headers, @PathVariable Long id, @RequestParam int vote);

    @PostMapping("/games/")
    PainelGame create(@RequestHeader Map<String, String> headers);
    
}