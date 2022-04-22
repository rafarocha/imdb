package com.games.imdb.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.games.imdb.domain.Game;
import com.games.imdb.domain.GameStep;
import com.games.imdb.domain.Movie;
import com.games.imdb.domain.RankUser;
import com.games.imdb.domain.to.CardMovie;
import com.games.imdb.domain.to.DetailGameStep;
import com.games.imdb.domain.to.PainelGame;
import com.games.imdb.domain.to.ResumeGameStepsWithRatings;
import com.games.imdb.repository.GameRepository;
import com.games.imdb.repository.MovieRepository;
import com.games.imdb.repository.RankRepository;
import com.games.imdb.service.client.OmdbApiClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GameService {

    @Autowired
    private OmdbApiClient movieGateway;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private RankRepository rankUserRepository;

    @Value("${app.host.url}")
    private String url;

    public Game get(Long id) {
        return gameRepository.getById(id);
    }

    public PainelGame painel(Long id) {
        Game game = gameRepository.getById(id);
        return mountPainel(game, game.getCurrentGameStep());
    }

    public PainelGame painel(Game game) {
        return mountPainel(game, game.getCurrentGameStep());
    }

    public PainelGame painel(Long id, int step) {
        Game game = gameRepository.getById(id);
        return mountPainel(game, game.getSpecificGameStep(step));
    }

    private PainelGame mountPainel(Game game, GameStep gameStep) {
        PainelGame painel = game.toPainelGame();

        Movie movie1 = movieRepository.getByImdbID(gameStep.getMovieId1());
        Movie movie2 = movieRepository.getByImdbID(gameStep.getMovieId2());

        CardUrl cardUrl = mountCardUrlForVotes(game.getId(), gameStep.getStep());

        CardMovie card1 = movie1.toCardMovie(cardUrl.url1);
        CardMovie card2 = movie2.toCardMovie(cardUrl.url2);

        painel.setCard1(card1);
        painel.setCard2(card2);
        painel.setStep(gameStep.getStep());
        return painel;
    }

    public PainelGame painel(Game game, int step) {
        GameStep gameStep = game.getSpecificGameStep(step);
        return mountPainel(game, gameStep);
    }

    public Game create(String user) {
        Game game = new Game(user);
        int index = 0;

        do {
            game.put(newStep(game, index));
        } while (index++ <= 3); // max 5 [0..4]

        return gameRepository.save(game.fillDocument());
    }

    private GameStep newStep(Game game, int currentStep) {

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
            }

        } while (race || (game.hasAnyMovie(m1, m2)));

        GameStep gameStep = new GameStep();
        gameStep.setStep(currentStep);
        gameStep.setMovieId1(m1.getImdbID());
        gameStep.setMovieId2(m2.getImdbID());
        return gameStep;
    }

    @Transactional
    public Game vote(Long id, int step, int vote) {
        Game game = gameRepository.getById(id);
        
        if (game == null)
            throw new RuntimeException("game nao existe id:" + id);

        if (game.isCanceled())
            throw new RuntimeException("game cancelado id: "  + id);

        GameStep gameStep = game.getSpecificGameStep(step);

        Movie movie1 = movieRepository.getByImdbID(gameStep.getMovieId1());
        Movie movie2 = movieRepository.getByImdbID(gameStep.getMovieId2());

        game.vote(step, vote, movie1, movie2, gameStep);
        gameRepository.save(game.fillDocument());

        if (game.isFinished()) {
            this.saveRank(game);
        }

        return game;
    }

    private void saveRank(Game game) {
        RankUser rank = rankUserRepository.getByUsername(game.getUser());
        if (rank == null) {
            rank = new RankUser(game.getUser());
        }

        //String document = game.getDocument(); // TODO tratar lazy load para os steps
        //game.postLoad();

        rank.update(game);
        rankUserRepository.save(rank);
    }

    // PUT http://localhost:8080/games/1?step=0&vote=2
    // no game 1 na etapa 0 e votando no card 2
    private static final String curl = "curl -X PUT %s/games/%s?step=%s&vote=%s";

    private CardUrl mountCardUrlForVotes(Long id, int step) {
        return new CardUrl(
                String.format(curl, url, id, step, 1),
                String.format(curl, url, id, step, 2));
    }

    public class CardUrl {
        private String url1, url2;

        public CardUrl(String url1, String url2) {
            this.url1 = url1;
            this.url2 = url2;
        }
    }

    public DetailGameStep toDetailGameStep(Long id, int step) {
        Game game = this.get(id);
        GameStep gameStep = game.getSpecificGameStep(step);

        Movie movie1 = movieRepository.getByImdbID(gameStep.getMovieId1());
        Movie movie2 = movieRepository.getByImdbID(gameStep.getMovieId2());

        return gameStep.toDetailGameStep(movie1, movie2);
    }

    public ResumeGameStepsWithRatings toResumeGameStepsWithRating(Long id) {
        Game game = this.get(id);

        List<DetailGameStep> details = new ArrayList<DetailGameStep>();
        for (GameStep step : game.getSteps()) {

            Movie movie1 = movieRepository.getByImdbID(step.getMovieId1());
            Movie movie2 = movieRepository.getByImdbID(step.getMovieId2());

            DetailGameStep detail = step.toDetailGameStep(movie1, movie2);
            details.add(detail);
        }

        return game.toResumeGameStepsWithRatings(details);
    }

    public void cancel(Long id) {
        Game game = this.get(id);
        game.cancel();
        gameRepository.save(game);
    }

}