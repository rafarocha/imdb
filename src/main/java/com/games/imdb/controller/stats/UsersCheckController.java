package com.games.imdb.controller.stats;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersCheckController {

    @GetMapping("/users/status/check")
    public String users() {
        return "Authorized user";
    }

    @GetMapping("/admin/status/check")
    public String admin() {
        return "Authorized manager";
    }

    @GetMapping("/logout")
    public String logout() {
        return "";
    }

}