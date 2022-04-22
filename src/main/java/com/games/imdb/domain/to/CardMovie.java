package com.games.imdb.domain.to;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
    private String commandToVote;

    @Override
    public String toString() {
        return title;
    }

}