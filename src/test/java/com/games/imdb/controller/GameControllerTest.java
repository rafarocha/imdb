package com.games.imdb.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.games.imdb.domain.Game;
import com.games.imdb.domain.Movie;
import com.games.imdb.domain.RankUser;
import com.games.imdb.domain.to.PainelGame;
import com.games.imdb.repository.GameRepository;
import com.games.imdb.repository.MovieRepository;
import com.games.imdb.repository.RankRepository;
import com.games.imdb.service.GameService;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
@TestPropertySource(locations = "classpath:application-test.properties")
public class GameControllerTest {

    @Autowired
    private GameController gameController;

    @Mock
    private GameService gameService;

    @Mock 
    private RankRepository rankRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private MovieRepository movieRepository;

    @BeforeEach
    void setMockOutput() {
        Game game = this.newGame();
        Movie movie1 = movie1();
        Movie movie2 = movie2();
        RankUser rank = rank(game);

        when(gameService.vote(1L, 1)).thenReturn(game);
        when(gameRepository.getById(1L)).thenReturn(game);

        when(movieRepository.getByImdbID("tt0x")).thenReturn(movie1);
        when(movieRepository.getByImdbID("tt0y")).thenReturn(movie2);
        
        when(gameRepository.save(game));
        when(gameRepository.getById(1L)).thenReturn(game);
        

        when(gameService.painel(game)).thenReturn( newPainel(game) );
    }

    @Test @Ignore
	public void voteOnlyStep1_RightResponse() {
        ResponseEntity<PainelGame> response = 
            gameController.vote(null, 1L, 1);

        // TODO assert's sobre mudanças no game e step, sem rank pois 1st step

		assertTrue(response.getStatusCode().value() == 200 );
	}

    private Game newGame() {
        Game game = new Game();
        game.setId(1L);
        game.setUser("rocha");
        return game;       
    }

    private PainelGame newPainel(Game game) {
        return PainelGame.builder()
                .id(game.getId())
                .user(game.getUser())
                .build();
    }

    private Movie movie1() {
        Movie movie = new Movie();
        movie.setTitle("Titanic");
        return movie;
    }

    private Movie movie2() {
        Movie movie = new Movie();
        movie.setTitle("Matrix");
        return movie;
    }

    private RankUser rank(Game game) {
        RankUser rank = new RankUser();
        rank.setId(1L);
        rank.setUsername(game.getUser());
        return rank;
    }
    
}