package com.games.imdb.domain;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.games.imdb.domain.to.DetailGameStep;
import com.games.imdb.domain.to.PainelGame;
import com.games.imdb.domain.to.ResumeGameStepsWithRatings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;

@Data
@Entity(name = "games")
@AllArgsConstructor
@Jacksonized
@Builder
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_game")
    private Long id;

    private String message;
    private String user;
    private String date;
    private int points;
    private int step;
    private int errors;
    private boolean finished;
    private boolean canceled;

    @Column(length = 1000)
    @Basic(fetch = FetchType.EAGER)
    @JsonIgnore
    public String document;

    @Transient
    @JsonIgnore
    private List<GameStep> steps = new ArrayList<GameStep>();

    public Game() {
        this.message = "Welcome";
        this.date = new SimpleDateFormat("dd/MM/yyyy hh:mm:MM").format(new Date());
    }

    public Game(String user) {
        this();
        this.user = user;
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

    // TODO problema com pattern builder e jackson para embedded list transiente
    @PostLoad
    public void postLoad() {
        String document = this.getDocument();
        if (document == null | document.isBlank())
            return;

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            List<GameStep> steps = objectMapper.readValue(document,
                    new TypeReference<List<GameStep>>() {
                    });
            this.steps = steps;
        } catch (IOException e) {
            log.error("erro ao deserializar game steps", e);
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
            log.error("erro ao serializar game em fill document", e);
            throw new RuntimeException(e);
        }
    }

    // TODO melhorar codigo .. lazy de atributo transiente e campo LOB
    // java.util.LinkedHashMap is in module java.base of loader 'bootstrap';
    // com.games.imdb.controller.game.GameStep is in unnamed module of loader
    // org.springframework.boot.devtools.restart.classloader.RestartClassLoade
    @JsonIgnore
    public GameStep getSpecificGameStep(int step) {
        validateLimitsStep(step);
        this.postLoad();

        List<GameStep> steps = this.getSteps();
        return steps.get(step);
    }

    @JsonIgnore
    public GameStep getCurrentGameStep() {
        this.postLoad(); // TODO melhorar aspecto lazy .. revisar metodos preConstruct/etc
        List<GameStep> steps = this.getSteps();

        int index = (this.finished) ? steps.size() - 1 : this.step;
        return steps.get(index);
    }

    @JsonIgnore
    public GameStep getNextGameStep() {
        this.postLoad(); // TODO melhorar aspecto lazy .. revisar metodos preConstruct/etc
        List<GameStep> steps = this.getSteps();

        int index = (this.finished) ? steps.size() - 1 : this.step + 1;
        if ( index == steps.size() ) return null; // haven't next step, currrent is the last
        return steps.get(index);
    }

    private void validateLimitsStepAndVote(int step, int vote) {
        validateLimitsStep(step);
        validateLimitsStep(vote);
    }

    public void validateLimitsStep(int step) {
        if (step > 4)
            throw new RuntimeException("etapa invalida .. [0..4]");
    }

    public void validateLimitsVote(int vote) {
        if (vote != 1 || vote != 2)
            throw new RuntimeException("voto invalido .. [0..1]");
    }

    public void vote(int step, int vote, Movie m1, Movie m2, GameStep gameStep) {
        validateLimitsStepAndVote(step, vote);

        // considerando empate como acerto .. para melhorias discutir usabilidade
        int mHigh = (m1.getImdbRating() >= m2.getImdbRating()) ? 1 : 2;

        gameStep.setVote(vote);
        gameStep.setResp(mHigh);
        gameStep.setItsRight(vote == mHigh);
        this.updatePointsAndErrors((mHigh == vote));

        if ( this.errors > 3 )
            throw new RuntimeException("game over .. 4 erros " + this.errors);            
        if (this.errors == 3)
            this.setMessage("you lost the game with 3 errors");

        String date = new SimpleDateFormat("dd/MM/yyyy hh:mm:MM").format(new Date());
        gameStep.setDateResponse(date);

        this.goToNextStep(step);
    }

    private void updatePointsAndErrors(boolean itIsRight) {
        if (itIsRight)
            this.points++;
        else
            this.errors++;
    }

    private void goToNextStep(int currentStep) {
        if (currentStep >= 4) {
            this.finished = true;
        }
        this.setStep(currentStep + 1);
    }

    @Deprecated // TODO reaproveitar quando for montar cardMovie
    private Long convertVotes(String votes) {
        String clean = votes.replace(",", "");
        return Long.valueOf(clean);
    }

    public void cancel() {
        this.canceled = true;
    }

    public ResumeGameStepsWithRatings toResumeGameStepsWithRatings(List<DetailGameStep> details) {
        return ResumeGameStepsWithRatings
                .builder()
                .message(this.getMessage())
                .user(this.getUser())
                .date(this.getDate())
                .details(details)
                .build();
    }

    public PainelGame toPainelGame() {
        String message = String.format("You have %s more stages besides this one. Good luck!", (4 - this.step));
        if ( 4 == this.step ) {
            message = "Final round!";
        }
        if ( this.finished ) {
            String finishMessage = "Just finished! You winner with %s points and %s errors.";
            message =  String.format(finishMessage, this.points, this.errors);
        }

        return PainelGame
            .builder()
            .id(this.getId())
            .message(message)
            .user(this.getUser())
            .date(this.getDate())
            .step(this.getStep())
            .build();
    }

}