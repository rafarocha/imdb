package com.games.imdb.controller;

import com.games.imdb.domain.Game;
import com.games.imdb.domain.GameStep;
import com.games.imdb.domain.PainelGame;
import com.games.imdb.service.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/games")
@Slf4j
public class GameController {

    @Autowired
    private GameService gameService;

    @PutMapping(value = "/{id}")
    public ResponseEntity<PainelGame> vote(@AuthenticationPrincipal User user,
            @PathVariable Long id, @RequestParam int step, @RequestParam int vote) {
        Game game = gameService.vote(id, step, vote);
        PainelGame painel = gameService.painel(game);
        return new ResponseEntity<PainelGame>(painel, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/painel")
    public ResponseEntity<PainelGame> painel(@AuthenticationPrincipal User user, @PathVariable Long id) {
        Game game = gameService.get(id);
        PainelGame painel = gameService.painel(game);
        return new ResponseEntity<PainelGame>(painel, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/painel/{step}")
    public ResponseEntity<PainelGame> painel(@AuthenticationPrincipal User user,
            @PathVariable Long id, @PathVariable String step) {
        Game game = gameService.get(id);
        PainelGame painel = gameService.painel(game);
        return new ResponseEntity<PainelGame>(painel, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Game> get(@AuthenticationPrincipal User user,
            @PathVariable Long id) {
        Game game = gameService.get(id);
        return new ResponseEntity<Game>(game, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PainelGame> create(@AuthenticationPrincipal User user) {
        Game game = gameService.create(user.getUsername());
        PainelGame painel = gameService.painel(game);
        return new ResponseEntity<PainelGame>(painel, HttpStatus.OK);
    }

    // TODO visualizar um painel especifico
    // TODO colocar a opcao de cancelar o game e iniciar outro
    // tentar errar 4 vezes

}