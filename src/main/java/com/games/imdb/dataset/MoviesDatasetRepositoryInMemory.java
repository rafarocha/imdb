package com.games.imdb.dataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Component
public class MoviesDatasetRepositoryInMemory {

    private List<String> titles = new ArrayList<String>();

    private Random random = new Random();

    public String random() {
        int index = random.nextInt(titles.size() - 1);
        return titles.get(index);
    }

    public void insert(String titleMovie) {
        titles.add(titleMovie);
    }

}