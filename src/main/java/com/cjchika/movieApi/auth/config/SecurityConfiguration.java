package com.cjchika.movieApi.auth.config;

import com.cjchika.movieApi.auth.services.AuthFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthFilterService authFilterService;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfiguration(AuthFilterService authFilterService, AuthenticationProvider authenticationProvider) {
        this.authFilterService = authFilterService;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless authentication
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll() // Allow auth-related endpoints without authentication
                        .requestMatchers("/error").permitAll()          // Allow access to /error without authentication
                        .anyRequest().authenticated())                 // Secure all other endpoints
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Stateless authentication
                .authenticationProvider(authenticationProvider)  // Custom authentication provider
                .addFilterBefore(authFilterService, UsernamePasswordAuthenticationFilter.class);  // Custom authentication filter

        return http.build();
    }
}
