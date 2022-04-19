package com.games.imdb.controller;

import java.util.List;

import com.games.imdb.domain.RankUser;
import com.games.imdb.domain.User;
import com.games.imdb.repository.RankRepository;

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
public class RankController {

    @Autowired
    private RankRepository rankRepository;

    @GetMapping(value = "/rank")
    public ResponseEntity<List<RankUser>> painel(@AuthenticationPrincipal User user, @PathVariable Long id) {
        List<RankUser> list = rankRepository.get10Hightest();
        return new ResponseEntity<List<RankUser>>(list, HttpStatus.OK);
    }

}