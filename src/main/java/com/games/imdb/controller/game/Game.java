package com.games.imdb.controller.game;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.games.imdb.controller.movies.Movie;

import lombok.Data;

@Data
@Entity(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_game")
    private Long id;

    private String message;
    private String user;
    private String date;
    private int points;
    private Long gameID;
    private int step;

    @Column(length = 1000)
    @Basic(fetch = FetchType.EAGER)
    public String document;

    @Transient
    private List<GameStep> steps = new ArrayList<GameStep>();

    public Game() {
        this.message = "Welcome";
        this.date = new SimpleDateFormat("dd/MM/yyyy hh:mm:MM").format(new Date());
    }

    public Game(String user) {
        this();
        this.user = user;
        this.step = 0;
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

    @PostLoad
    public void postLoad() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            List<GameStep> steps = objectMapper.readValue(this.document,
                    new TypeReference<List<GameStep>>() {
                    });
            this.steps = steps;
        } catch (IOException ex) {
            // logger.error("Unexpected IOEx decoding json from database: " + dbData);
            return;
        }
    }

    public Game fillDocument() {
        if (this.steps == null || this.steps.isEmpty())
            return this;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.document = objectMapper.writeValueAsString(this.steps);
            return this;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}