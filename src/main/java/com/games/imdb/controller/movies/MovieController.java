package com.games.imdb.controller.movies;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.games.imdb.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/movies")
    public ResponseEntity<String> runningGame(@AuthenticationPrincipal User user) {

        try {
            List<Movie> movies = movieRepository.findAll();
            String json = new ObjectMapper().writeValueAsString(movies);
            return new ResponseEntity<String>(json, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            log.error("erro ao transformar movies para json", e);
            throw new RuntimeException(e);
        }
    }

}
