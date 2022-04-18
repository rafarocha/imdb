package com.games.imdb.controller.game;

import lombok.Data;

@Data
public class CardMovie {

    private String imdbID;
    private String title;
    private String year;
    private String genre;
    private String actors;
    private String country;
    private String poster;
    private Double imdbRating;
    private Long imdbVotes;

    @Override
    public String toString() {
        return title;
    }

}