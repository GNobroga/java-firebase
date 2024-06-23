package com.gabriel.firebase.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        return http
            .csrf(options -> options.disable())
            .sessionManagement(options -> options.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .cors(options -> options.disable())
            .authorizeHttpRequests(options -> options.anyRequest().authenticated())
            .addFilterBefore(new SecurityFirebaseTokenFilter(userDetailsService), UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
