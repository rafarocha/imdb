package com.games.imdb.controller.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.games.imdb.controller.movies.Movie;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/game")
@Slf4j
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameMovieClient movieClient;

    // TODO colocar a opcao de cancelar o game e iniciar outro

    @Value("${app.host.url}")
    private String url;

    // http://localhost:8080/game/1?step=0&vote=1
    @GetMapping(value = "/{id}")
    public ResponseEntity<String> runningGame(@AuthenticationPrincipal User user,
            @PathVariable String id, @RequestParam int step, @RequestParam int vote) {

        return null;
    }

    @GetMapping(value = "new") // TODO mudar para post
    public ResponseEntity<String> newGame(@AuthenticationPrincipal User user) {

        Game game = gameService.newGame(user.getUsername());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(game);
            return new ResponseEntity<String>(json, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            log.error("erro ao iniciar game", e);
            throw new RuntimeException(e);
        }
    }

}

/**
 * 
 * String json = objectMapper.writeValueAsString(gamming);
 * return new ResponseEntity<String>(json, HttpStatus.OK);
 * 
 * } catch (JsonProcessingException e) {
 * log.error("erro ao iniciar game", e);
 * JSONObject jo = new JSONObject();
 * jo.put("message", "erro ao iniciar game");
 * jo.put("ticket", DigestUtils.sha256Hex("erro ao iniciar game"));
 * return new ResponseEntity<>(jo.toString(), HttpStatus.SERVICE_UNAVAILABLE);
 * }
 * 
 * 
 * // TODO setar URL apenas quando tiver o ID - mas acho que nao sera esse
 * objeto
 * // .. sera o TO .. esse é um entity
 * // method > runningGame
 * String urlVote1 = url + "/game/" + game.getGameID() + "?step=0&vote=1";
 * String urlVote2 = url + "/game/" + game.getGameID() + "?step=0&vote=2";
 */