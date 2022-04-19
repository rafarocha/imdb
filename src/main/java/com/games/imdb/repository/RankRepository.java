package com.games.imdb.repository;

import java.util.List;

import com.games.imdb.domain.Movie;
import com.games.imdb.domain.RankUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepository extends JpaRepository<RankUser, Long> {

    @Query("from rankusers order by balance desc")
    List<RankUser> get10Hightest();

    @Query("from rankusers where username = :username")
    RankUser getByUsername(@Param("username") String username);

}