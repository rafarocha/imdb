package com.games.imdb.service;

import com.games.imdb.dataset.MoviesDatasetRepositoryInMemory;
import com.games.imdb.service.client.IMDBClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class IMDBHolderFallback implements IMDBClient {

    public IMDBHolderFallback() {
    }

    @Autowired
    private IMDBClient imdbClient;

    @Value("${app.imdb.apikey}")
    private String apikey;

    @Autowired
    private MoviesDatasetRepositoryInMemory datasetRepositoryInMemory;

    @Override
    public String movies(String t, String apikey) {
        return imdbClient.movies(t, apikey); // implementado como retry
    }

}