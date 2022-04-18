package com.games.imdb.controller.game;

import lombok.Data;

@Data
public class CardMovie {

    private String imdbID;
    private String poster;
    private String title;
    private String year;
    private String released;
    private String genre;
    private String actors;
    private String country;
    private String plot;
    private String urlVote;

    @Override
    public String toString() {
        return title;
    }

}