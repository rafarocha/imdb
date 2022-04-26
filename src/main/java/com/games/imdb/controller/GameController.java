package com.games.imdb.controller;

import com.games.imdb.domain.Game;
import com.games.imdb.domain.RankUser;
import com.games.imdb.domain.to.DetailGameStep;
import com.games.imdb.domain.to.PainelGame;
import com.games.imdb.domain.to.ResumeGameStepsWithRatings;
import com.games.imdb.repository.RankRepository;
import com.games.imdb.service.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @Autowired
    private RankRepository rankRepository;

    @PutMapping(value = "/{id}")
    public ResponseEntity<PainelGame> vote(@AuthenticationPrincipal User user,
            @PathVariable Long id, @RequestParam int vote) {
        Game game = gameService.vote(id, vote);
        PainelGame painel = gameService.painel(game);
        return new ResponseEntity<PainelGame>(painel, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/last")
    public ResponseEntity<RankUser> lastVote(@AuthenticationPrincipal User user,
            @PathVariable Long id, @RequestParam int vote) {
        gameService.vote(id, vote);
        RankUser rank = rankRepository.getByUsername(user.getUsername());
        return new ResponseEntity<RankUser>(rank, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/resume")
    public ResponseEntity<ResumeGameStepsWithRatings> resume(@AuthenticationPrincipal User user, @PathVariable Long id) {
        ResumeGameStepsWithRatings resume = gameService.toResumeGameStepsWithRating(id);
        return new ResponseEntity<ResumeGameStepsWithRatings>(resume, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/step/{step}/answer")
    public ResponseEntity<DetailGameStep> step(@AuthenticationPrincipal User user,
            @PathVariable Long id, @PathVariable int step) {
        DetailGameStep detail = gameService.toDetailGameStep(id, step);
        return new ResponseEntity<DetailGameStep>(detail, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/step/{step}")
    public ResponseEntity<PainelGame> card(@AuthenticationPrincipal User user,
            @PathVariable Long id, @PathVariable int step) {
        PainelGame painel = gameService.painel(id, step);
        return new ResponseEntity<PainelGame>(painel, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Game> get(@AuthenticationPrincipal User user,
            @PathVariable Long id) {
        Game game = gameService.get(id);
        return new ResponseEntity<Game>(game, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<PainelGame> create(Authentication auth) {
        Game game = gameService.create(auth.getName());
        PainelGame painel = gameService.painel(game);
        return new ResponseEntity<PainelGame>(painel, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancel(@AuthenticationPrincipal User user, @PathVariable Long id) {
        gameService.cancel(id);
        return new ResponseEntity<String>("game " + id + " canceled", HttpStatus.OK);        
    }

    // TODO resolver bug calculo ranking e salvar steps
    // TODO ajustar os calculos de pontos e pontuacao ranking
    // TODO adicionar swagger
    // TODO comecar testes ...

}