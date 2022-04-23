package com.games.imdb.repository;

import java.util.List;

import com.games.imdb.domain.Game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("from games where finished = false")
    List<Game> getAllNotFinished();

}