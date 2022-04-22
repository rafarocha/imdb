package com.games.imdb.domain;

import javax.persistence.Embeddable;

import com.games.imdb.domain.to.DetailGameStep;

import lombok.Data;

@Data
@Embeddable
public class GameStep {

    private int step; // sequenciar as etapas: 0, 1, 2 .. sao valores possiveis

    // private CardMovie movie1;
    // private CardMovie movie2;

    private String movieId1;
    private String movieId2;

    private int vote; // 0 nao votou, 1 votou em 1, 2 votou em 2
    private int resp; // 0 nao votou .. indica qual o maior, se 1 ou 2 após voto
    private boolean itsRight;
    private String dateResponse; // quando respondeu o card

    public boolean hasAnyMovie(Movie m1, Movie m2) {
        if (this.movieId1 == null || this.movieId2 == null)
            return false;

        if (m1 == null || m2 == null)
            return false;

        boolean m1m1 = (this.movieId1.equals(m1.getImdbID()));
        boolean m1m2 = (this.movieId1.equals(m2.getImdbID()));
        boolean m2m2 = (this.movieId2.equals(m2.getImdbID()));

        return (m1m1 || m1m2 || m2m2);
    }

    public DetailGameStep toDetailGameStep(Movie m1, Movie m2) {
        return DetailGameStep
            .builder()
            .gameStep(this)
            .rating1(m1.toRatingMovie())
            .rating2(m2.toRatingMovie())
            .build();
    }

}