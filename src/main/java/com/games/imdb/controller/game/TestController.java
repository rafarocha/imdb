package com.games.imdb.controller.game;

import com.games.imdb.controller.movies.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping(value = "/new")
    public ResponseEntity<String> runningGame() {

        movieRepository.insert("hi");

        return new ResponseEntity<String>("sucess", HttpStatus.OK);
    }

}