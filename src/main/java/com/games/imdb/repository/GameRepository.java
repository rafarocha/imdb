package com.games.imdb.repository;

import com.games.imdb.domain.Game;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

}