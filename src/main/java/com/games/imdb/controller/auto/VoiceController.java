package com.games.imdb.controller.auto;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.games.imdb.domain.Game;
import com.games.imdb.domain.RankUser;
import com.games.imdb.domain.to.PainelGame;
import com.games.imdb.service.GameService;
import com.games.imdb.service.client.GameClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class VoiceController {

    @Autowired
    private GameClient gameClient;

    @Autowired
    private GameService gameService;

    @GetMapping(value = "/votes/{gameId}/{vote}")
    public ResponseEntity<PainelGame> vote(@AuthenticationPrincipal User user, HttpServletRequest request,
            @PathVariable Long gameId, @PathVariable int vote) {

        Map<String, String> headers = HelperController.getAllHeaders(request);
        PainelGame painel = gameClient.vote(headers, gameId, vote);

        return new ResponseEntity<PainelGame>(painel, HttpStatus.OK);
    }      
    
    @GetMapping(value = "/votes/{gameId}/{vote}/last")
    public ResponseEntity<RankUser> lastVote(@AuthenticationPrincipal User user, HttpServletRequest request,
            @PathVariable Long gameId, @PathVariable int vote) {

        Map<String, String> headers = HelperController.getAllHeaders(request);
        RankUser rank = gameClient.lastVote(headers, gameId, vote);
        
        return new ResponseEntity<RankUser>(rank, HttpStatus.OK);
    }
    
    @GetMapping(value = "/new")
    public ResponseEntity<PainelGame> startNewGame(@AuthenticationPrincipal User user, HttpServletRequest request) {
        Map<String, String> headers = HelperController.getAllHeaders(request);
        PainelGame painel = gameClient.create(headers);
        return new ResponseEntity<PainelGame>(painel, HttpStatus.OK);
    }

}
