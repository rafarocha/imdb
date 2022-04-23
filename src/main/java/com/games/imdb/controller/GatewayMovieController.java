package com.games.imdb.controller;

import com.games.imdb.dataset.MoviesDatasetRepositoryInMemory;
import com.games.imdb.service.client.IMDBClient;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class GatewayMovieController {

    @Autowired
    private IMDBClient imdbClient;

    @Autowired
    private MoviesDatasetRepositoryInMemory datasetRepositoryInMemory;

    @Value("${app.imdb.apikey}")
    private String apikey;

    @Value("${app.imdb.url}")
    private String url;

    @GetMapping(value = "/gateway")
    public ResponseEntity<String> movie() {
        String titleRandom = datasetRepositoryInMemory.random();
        try {

            String json = imdbClient.movies(titleRandom, apikey);
            if (movieNotFound(json)) {
                JSONObject jo = new JSONObject();
                jo.put("title", titleRandom);
                jo.put("message", MOVIE_NOT_FOUND);
                return new ResponseEntity<>(jo.toString(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(json, HttpStatus.OK);

        } catch (Exception e) {
            log.error(getMessageError(titleRandom), e);
            JSONObject jo = new JSONObject();
            jo.put("title", titleRandom);
            jo.put("message", SERVICE_UNAVAILABLE);
            jo.put("serverUrl", SERVICE_UNAVAILABLE);
            jo.put("ticket", DigestUtils.sha256Hex(titleRandom));
            return new ResponseEntity<>(jo.toString(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private String getMessageError(String title) {
        return MOVIE_NOT_FOUND + ": " + title;
    }

    private static final String MOVIE_NOT_FOUND = "filme nao encontrado";
    private static final String SERVICE_UNAVAILABLE = "servico fora do ar";

    private boolean movieNotFound(String json) {
        return (json.contains("Response")
                && json.contains("False")
                && json.contains("Error")
                && json.contains("Movie not found"));
    }

}