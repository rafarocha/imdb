package com.games.imdb.controller.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.games.imdb.controller.movies.Movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GameService {

    @Autowired
    private GameMovieClient movieClient;

    public Game newGame(String user) {

        Game game = new Game(user);

        int steps = 0;
        do {
            game.put(createNewStep(game, steps));
        } while (steps++ <= 3); // max 5 steps

        // TODO setar o ID ao pegar do repository antes de retornar
        return game;
    }

    private GameStep createNewStep(Game game, int foot) {

        Movie m1 = null, m2 = null;
        boolean race = false;
        do {
            try {
                String movie1 = movieClient.movie();
                String movie2 = movieClient.movie();

                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    m1 = objectMapper.readValue(movie1, Movie.class);
                    m2 = objectMapper.readValue(movie2, Movie.class);
                } catch (JsonProcessingException e) {
                    log.error("erro ao deserializar movies", e);
                }
            } catch (Exception e) {
                log.error("erro ao obter movie .. tentando de novo", e);
            }
            race = !((m1 != null) && (m2 != null));

        } while (race || (game.hasAnyMovie(m1, m2))); // TODO consertar

        GameStep step = new GameStep();
        step.setFoot(foot);
        step.setMovieId1(m1.getImdbID());
        step.setMovieId2(m2.getImdbID());

        return step;
    }

    // TODO reaproveitar quando for montar cardMovie
    private Long convertVotes(String votes) {
        String clean = votes.replace(",", "");
        return Long.valueOf(clean);
    }

}