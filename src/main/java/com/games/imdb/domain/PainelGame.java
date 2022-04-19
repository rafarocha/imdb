package com.games.imdb.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PainelGame {

    private Long id;
    private String message;
    private String user;
    private String date;
    private int step;
    private CardMovie card1;
    private CardMovie card2;

}