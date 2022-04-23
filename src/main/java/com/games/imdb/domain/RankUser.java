package com.games.imdb.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "rankusers")
public class RankUser {

    public RankUser() {}
    public RankUser(String username) {
        this(); this.username = username;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rank")
    private Long id;

    private String username;
    private long totalGames;
    private long totalPoints;
    private long totalErrors;
    private long balance;

    public void update(Game game) {
        String document = game.getDocument();
        game.postLoad();;
        for (GameStep step : game.getSteps()) {
            if (step.isItsRight())
                this.totalPoints += 1;
            else
                this.totalErrors += 1;
        }
        this.balance = this.totalPoints - this.totalErrors;
        this.totalGames += 1;
    }

}