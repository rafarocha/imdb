package com.games.imdb.domain.to;

import com.games.imdb.domain.GameStep;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetailGameStep {

    private GameStep gameStep;
    private RatingMovie rating1;
    private RatingMovie rating2;

}