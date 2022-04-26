package com.games.imdb.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class GameTest {

    private Game game;
    private GameStep g1;

    private Movie movie1;
    private Movie movie2;

    @Before
    public void before() {
        this.g1 = g1();
        this.game = newGame();
        this.movie1 = movie1();
        this.movie2 = movie2();
    }

    @Test
	public void cancel() {
        game.cancel();
		assertTrue(game.isCanceled());
	}

    @Test 
    public void voteWhenStep0ToAnyCardIndependentResult() {
        game.vote(1, movie1, movie2);

        assertEquals(g1.getVote(), 1);
        assertEquals(g1.getResp(), 2);
        assertFalse(g1.isItsRight());
        assertTrue(game.getStep() == 1);
    }

    private Game newGame() {
        Game game = new Game();
        game.put(g1);

        game.setId(1L);
        game.setUser("rocha");
        game.setDocument("{}");
        return game;       
    }

    private GameStep g1() {
        GameStep g1 = new GameStep();
        g1.setVote(1);
        return g1;
    }

    private Movie movie1() {
        Movie movie = new Movie();
        movie.setTitle("Titanic");
        movie.setImdbRating(8.9);
        return movie;
    }

    private Movie movie2() {
        Movie movie = new Movie();
        movie.setTitle("Matrix");
        movie.setImdbRating(9.3);
        return movie;
    }
    
}