package com.games.imdb.controller.game;

import java.util.Map;

import com.games.imdb.controller.movies.Movie;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardGame {

    private Game game;
    private Map<String, Movie> movies;

}