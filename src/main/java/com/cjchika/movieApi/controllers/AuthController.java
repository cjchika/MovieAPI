package com.cjchika.movieApi.controllers;

import com.cjchika.movieApi.auth.entities.RefreshToken;
import com.cjchika.movieApi.auth.entities.User;
import com.cjchika.movieApi.auth.services.AuthService;
import com.cjchika.movieApi.auth.services.JwtService;
import com.cjchika.movieApi.auth.services.RefreshTokenService;
import com.cjchika.movieApi.auth.utils.AuthResponse;
import com.cjchika.movieApi.auth.utils.LoginRequest;
import com.cjchika.movieApi.auth.utils.RefreshTokenRequest;
import com.cjchika.movieApi.auth.utils.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerHandler(@RequestBody RegisterRequest registerRequest){
        AuthResponse response = authService.register(registerRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> registerHandler(@RequestBody LoginRequest loginRequest){
        AuthResponse response = authService.login(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        User user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(user);
        AuthResponse response = AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
