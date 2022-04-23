package com.games.imdb.config;

import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import com.games.imdb.dataset.MoviesDatasetReader;
import com.games.imdb.dataset.MoviesDatasetRepositoryInMemory;
import com.games.imdb.dataset.SimplePositionBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ImdbConfig {

    @Autowired
    private MoviesDatasetReader datasetReader;

    @Autowired
    private MoviesDatasetRepositoryInMemory repositoryInMemory;

    @PostConstruct
    private void startup() throws Exception {
        List<SimplePositionBean> dataset = datasetReader.read();
        for (SimplePositionBean bean : dataset) {
            repositoryInMemory.insert(bean.getTitle());
        }
    }

}