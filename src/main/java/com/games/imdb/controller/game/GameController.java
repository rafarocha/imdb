package com.games.imdb.controller.game;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.games.imdb.controller.movies.Movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    // TODO colocar a opcao de cancelar o game e iniciar outro

    @Value("${app.host.url}")
    private String url;

    @PutMapping(value = "/{id}")
    public String vote(@AuthenticationPrincipal User user,
            @PathVariable Long id, @RequestParam int step, @RequestParam int vote) {

        gameService.vote(id, step, vote);

        return "sucess";
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<String> get(@AuthenticationPrincipal User user,
            @PathVariable String id) {

        return null;
    }

    @PostMapping
    public ResponseEntity<String> create(@AuthenticationPrincipal User user) {

        BoardGame board = gameService.create(user.getUsername());
        Game game = board.getGame();
        Map<String, Movie> movies = board.getMovies();

        // jogar na camada service
        PainelGame painel = new PainelGame();
        painel.setId(game.getId());
        painel.setMessage(game.getMessage());
        painel.setUser(game.getUser());
        painel.setDate(game.getDate());
        painel.setPoints(game.getPoints());
        painel.setGameID(game.getGameID());
        painel.setStep(0);

        GameStep stepZero = game.getSteps().get(0);
        Movie movie1 = movies.get(stepZero.getMovieId1());
        Movie movie2 = movies.get(stepZero.getMovieId2());

        CardMovie card1 = new CardMovie();
        card1.setImdbID(movie1.getImdbID());
        card1.setTitle(movie1.getTitle());
        card1.setYear(movie1.getYear());
        card1.setReleased(movie1.getReleased());
        card1.setGenre(movie1.getGenre());
        card1.setActors(movie1.getActors());
        card1.setCountry(movie1.getCountry());
        card1.setPlot(movie1.getPlot());
        card1.setPoster(movie1.getPoster());
        String urlCard1 = "curl -X PUT " + url + "/games/" + game.getId() + "?step=0&vote=1";
        card1.setUrlVote(urlCard1);

        CardMovie card2 = new CardMovie();
        card2.setImdbID(movie2.getImdbID());
        card2.setTitle(movie2.getTitle());
        card2.setYear(movie2.getYear());
        card2.setReleased(movie2.getReleased());
        card2.setGenre(movie2.getGenre());
        card2.setActors(movie2.getActors());
        card2.setCountry(movie2.getCountry());
        card2.setPlot(movie2.getPlot());
        card2.setPoster(movie2.getPoster());
        String urlCard2 = "curl -X PUT " + url + "/games/" + game.getId() + "?step=0&vote=2";
        card2.setUrlVote(urlCard2);

        painel.setCard1(card1);
        painel.setCard2(card2);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(painel);
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