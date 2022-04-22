package com.games.imdb.domain.to;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RatingMovie {

    private String imdbID;
    private String title;
    private String poster;
    private Double imdbRating;

}