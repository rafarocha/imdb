package com.games.imdb.controller.game;

import lombok.Data;

@Data
public class PainelGame {

    private Long id;
    private String message;
    private String user;
    private String date;
    private int points;
    private Long gameID;
    private int step;
    private CardMovie card1;
    private CardMovie card2;

}