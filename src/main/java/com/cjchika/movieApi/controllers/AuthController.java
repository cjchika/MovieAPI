package com.cjchika.movieApi.controllers;

import com.cjchika.movieApi.auth.services.AuthService;
import com.cjchika.movieApi.auth.utils.AuthResponse;
import com.cjchika.movieApi.auth.utils.LoginRequest;
import com.cjchika.movieApi.auth.utils.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
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
}
