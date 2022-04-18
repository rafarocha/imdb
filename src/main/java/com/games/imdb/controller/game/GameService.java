package com.games.imdb.controller.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.games.imdb.controller.movies.Movie;
import com.games.imdb.controller.movies.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GameService {

    @Autowired
    private GameMovieClient movieGateway;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GameRepository gameRepository;

    public BoardGame create(String user) {

        Game game = new Game(user);
        Map<String, Movie> movies = new HashMap<String, Movie>();
        BoardGame board = new BoardGame(game, movies);

        int steps = 0;
        do {
            game.put(createStep(board, steps));
        } while (steps++ <= 3); // max 5 steps

        gameRepository.save(game.fillDocument());

        return board;
    }

    private GameStep createStep(BoardGame board, int foot) {

        Movie m1 = null, m2 = null;
        boolean race = false;
        do {
            try {
                String movie1 = movieGateway.movie();
                String movie2 = movieGateway.movie();

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

            if (race == false) {
                movieRepository.save(m1);
                movieRepository.save(m2);

                board.getMovies().put(m1.getImdbID(), m1);
                board.getMovies().put(m2.getImdbID(), m2);
            }

        } while (race || (board.getGame().hasAnyMovie(m1, m2))); // TODO consertar

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

    public void vote(Long id, int step, int vote) {

        Game game = gameRepository.getById(id);
        String document = game.getDocument();
        List<GameStep> steps = game.getSteps();
        GameStep gameStep = steps.get(step);

        Movie movie1 = movieRepository.getByImdbID(gameStep.getMovieId1());
        System.out.println(movie1);

    }

}