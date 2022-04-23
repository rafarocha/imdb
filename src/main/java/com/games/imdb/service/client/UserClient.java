package com.games.imdb.service.client;

import com.games.imdb.config.FeignBasicConfig;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${app.host.url}", value = "user", configuration = FeignBasicConfig.class)
public interface UserClient {

    @GetMapping("/users/{username}")
    public UserDetails userExists(@PathVariable("username") String username );

    @PostMapping("/users/{username}/{role}/{password}")
    public String add(@PathVariable("username") String username, 
        @PathVariable("password") String password, @PathVariable("role") String role);
    
}