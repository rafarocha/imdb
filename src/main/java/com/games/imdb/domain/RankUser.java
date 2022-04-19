package com.games.imdb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;

@Data
@Entity(name = "rankusers")
@Builder
public class RankUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rank")
    private Long id;

    private String username;
    private Long totalGames;
    private Long totalPoints;
    private Long totalErrors;
    private Long balance;

    public void update(boolean itIsRight) {
        if (itIsRight)
            this.totalPoints += 1;
        else
            this.totalErrors += 1;
        this.balance = this.totalPoints - this.totalErrors;
    }

}