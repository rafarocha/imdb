package com.games.imdb.controller;

import java.util.List;

import com.games.imdb.domain.Movie;
import com.games.imdb.domain.User;
import com.games.imdb.repository.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> runningGame(@AuthenticationPrincipal User user) {
        List<Movie> movies = movieRepository.findAll();
        return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
    }

    @GetMapping(value = "/movies/{imdbID}")
    public ResponseEntity<Movie> get(@AuthenticationPrincipal User user, @PathVariable String imdbID) {
        Movie movie = movieRepository.getByImdbID(imdbID);
        return new ResponseEntity<Movie>(movie, HttpStatus.OK);
    }

}
