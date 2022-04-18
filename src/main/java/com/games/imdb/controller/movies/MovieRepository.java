package com.games.imdb.controller.movies;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    interface Queries {
        String INSERT = "insert into movies (title) " +
                "VALUES (:title)";
    }

    @Modifying
    @Query(value = Queries.INSERT, nativeQuery = true)
    @Transactional
    void insert(@Param("title") String title);

    @Query("from movies where imdbID = :imdbID")
    Movie getByImdbID(@Param("imdbID") String imdbID);

}