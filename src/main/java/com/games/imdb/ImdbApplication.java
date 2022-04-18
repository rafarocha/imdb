package com.games.imdb;

import com.games.imdb.config.FeignBasicConfig;
import com.games.imdb.config.ImdbConfig;
import com.games.imdb.config.SecurityConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@EnableFeignClients
@SpringBootApplication
@Import({ ImdbConfig.class, SecurityConfig.class, FeignBasicConfig.class })
public class ImdbApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImdbApplication.class, args);
	}

}
