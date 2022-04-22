package com.games.imdb.domain.to;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResumeGameStepsWithRatings {

    private Long id;
    private String message;
    private String user;
    private String date;
    private List<DetailGameStep> details;
    
}