package com.example.youtubeclone.youtubeclone.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class LoginConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/login", "/logout").permitAll() // Permit access to login and logout
                                .requestMatchers("/**").authenticated() // Permit access to login and logout
                                .anyRequest().authenticated()

                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/customlogin")
                                .permitAll().defaultSuccessUrl("/",true)
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                );
        return http.build();
    }

}