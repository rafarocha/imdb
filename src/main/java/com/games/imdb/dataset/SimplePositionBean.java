package com.games.imdb.dataset;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class SimplePositionBean {
    @CsvBindByPosition(position = 2)
    private String title;

}