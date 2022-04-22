package com.games.imdb.controller.auto;

import com.games.imdb.domain.Game;
import com.games.imdb.service.client.GameClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/autos")
@Slf4j
public class AutomationController {

    @Autowired
    private GameClient gameClient;

    @GetMapping(value = "/create-game-and-vote-all-card2")
    public ResponseEntity<String> createGameAndVoteAllCard2() {
        Game game = gameClient.create();
        gameClient.vote(game.getId(), 0, 2);
        gameClient.vote(game.getId(), 1, 2);
        gameClient.vote(game.getId(), 2, 2);
        gameClient.vote(game.getId(), 3, 2);
        gameClient.vote(game.getId(), 4, 2);
        return new ResponseEntity<String>("sucess", HttpStatus.OK);
    }

    @GetMapping(value = "/create-game-and-vote-all-card3-failed")
    public ResponseEntity<String> createGameAndVoteCard3Failed() {
        Game game = gameClient.create();
        gameClient.vote(game.getId(), 0, 3);
        return new ResponseEntity<String>("sucess", HttpStatus.OK);
    }
    
}