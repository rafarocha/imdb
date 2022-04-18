package com.games.imdb.controller.game;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.games.imdb.controller.movies.Movie;

import lombok.Data;

@Data
public class Game {

    private String message;
    private String user;
    private String date;
    private int points;
    private Long gameID;
    private List<GameStep> steps = new ArrayList<GameStep>();

    public Game() {
        this.message = "Welcome";
        this.date = new SimpleDateFormat("dd/MM/yyyy hh:mm:MM").format(new Date());
    }

    public Game(String user) {
        this();
        this.user = user;
    }

    public Game put(GameStep step) {
        this.steps.add(step);
        return this;
    }

    public boolean hasAnyMovie(Movie m1, Movie m2) {
        if (steps == null || steps.isEmpty())
            return false;

        for (GameStep step : steps) {
            if (step.hasAnyMovie(m1, m2)) {
                return true;
            }
        }
        return false;
    }

}