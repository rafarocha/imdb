package com.games.imdb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.WebSessionServerLogoutHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                http
                                .csrf().disable()
                                .authorizeRequests()
                                .antMatchers("/managers/status/check").hasAnyAuthority("ADMIN")
                                .antMatchers("/users/status/check").hasRole("USER")
                                .antMatchers("/imdb").hasAnyRole("USER", "SYSTEM")
                                .antMatchers("/games").hasAnyRole("USER", "SYSTEM")
                                .antMatchers("/movies").hasAnyRole("USER", "SYSTEM")
                                // .anyRequest().authenticated()
                                .and()
                                .httpBasic()
                                .and()
                                .sessionManagement()
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

        public SecurityWebFilterChain http(ServerHttpSecurity http) throws Exception {
                DelegatingServerLogoutHandler logoutHandler = new DelegatingServerLogoutHandler(
                                new WebSessionServerLogoutHandler(), new SecurityContextServerLogoutHandler());

                http
                                .authorizeExchange((exchange) -> exchange.anyExchange().authenticated())
                                .logout((logout) -> logout.logoutHandler(logoutHandler));
                return http.build();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth
                                .inMemoryAuthentication()
                                .withUser("rocha")
                                .password("{noop}123")
                                .roles("USER")
                                .and()
                                .withUser("lima")
                                .password("{noop}123")
                                .roles("USER")
                                .and()
                                .withUser("admin")
                                .password("{noop}123")
                                .roles("ADMIN")
                                .and()
                                .withUser("system")
                                .password("{noop}123")
                                .roles("SYSTEM");
        }

        @Bean
        @Override
        public UserDetailsService userDetailsService() {
                // User Role
                UserDetails theUser = User.withUsername("sergey")
                                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
                                .password("12345678").roles("USER").build();

                // Manager Role
                UserDetails theManager = User.withUsername("john")
                                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
                                .password("87654321").roles("MANAGER").build();

                InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

                userDetailsManager.createUser(theUser);
                userDetailsManager.createUser(theManager);

                return userDetailsManager;
        }

}