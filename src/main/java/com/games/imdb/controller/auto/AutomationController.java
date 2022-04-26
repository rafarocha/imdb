package com.games.imdb.controller.auto;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.games.imdb.domain.Game;
import com.games.imdb.domain.to.PainelGame;
import com.games.imdb.service.GameService;
import com.games.imdb.service.client.GameClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autos")
public class AutomationController {

    @Autowired
    private GameClient gameClient;

    @Autowired
    private GameService gameService;

    @GetMapping(value = "/create-game-and-vote-right-all-answers")
    public ResponseEntity<String> createGameAndVoteRightAllAnswers(@AuthenticationPrincipal User user, HttpServletRequest request) {
        Map<String, String> headers = HelperController.getAllHeaders(request);
        PainelGame painel = gameClient.create(headers);
        Game game = gameService.get(painel.getId());
        allCardsVoteRightAnswers(headers, game, user);
        return new ResponseEntity<String>("sucess", HttpStatus.OK);
    }

    @GetMapping(value = "/create-game-and-vote-invalid")
    public ResponseEntity<String> createGameAndVoteCard3Failed(@AuthenticationPrincipal User user, HttpServletRequest request) {
        Map<String, String> headers = HelperController.getAllHeaders(request);
        PainelGame painel = gameClient.create(headers);
        gameClient.vote(headers, painel.getId(), 3);
        return new ResponseEntity<String>("sucess", HttpStatus.OK);
    }

    private void allCardsVoteRightAnswers(Map<String, String> headers, Game game, User user) {
        int vote = gameService.getRightAnswer(game, 0);
        gameClient.vote(headers, game.getId(), vote);

        vote = gameService.getRightAnswer(game, 1);
        gameClient.vote(headers, game.getId(), vote);

        vote = gameService.getRightAnswer(game, 2);
        gameClient.vote(headers, game.getId(), vote);

        vote = gameService.getRightAnswer(game, 3);
        gameClient.vote(headers, game.getId(), vote);

        vote = gameService.getRightAnswer(game, 4);
        gameClient.vote(headers, game.getId(), vote);
    }
    
}