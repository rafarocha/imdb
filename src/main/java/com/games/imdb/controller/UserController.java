package com.games.imdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @GetMapping("/{username}")
    public ResponseEntity<UserDetails> userExists(@PathVariable("username") String username ) {
        if ( !inMemoryUserDetailsManager.userExists(username) )
            return ResponseEntity.noContent().build();

        UserDetails userDetails = inMemoryUserDetailsManager.loadUserByUsername(username);
        return new ResponseEntity<UserDetails>(userDetails, HttpStatus.OK);
    }

    @PostMapping("/{username}/{role}/{password}")
    public ResponseEntity<UserDetails> add(@PathVariable("username") String username, 
        @PathVariable("password") String password, @PathVariable("role") String role) {
        
        UserDetails theUser = User.withUsername(username)
            .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
            .password(password).roles(role).build();

        inMemoryUserDetailsManager.createUser(theUser);
        return new ResponseEntity<UserDetails>(theUser, HttpStatus.CREATED);
    }
    
}