package org.enterpriseapp.artapi.security.controllers;

import jakarta.validation.Valid;
import org.enterpriseapp.artapi.security.requests.LoginRequest;
import org.enterpriseapp.artapi.security.services.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService){
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }



    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@Valid @RequestBody LoginRequest loginRequest) {
        // Authenticate the user
        System.out.println("activated");
        System.out.println(loginRequest.getUsername());
        System.out.println(loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        System.out.println("past authenticationmanager");

        // Set the authentication in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println("past securityContext");

        String token = tokenService.generateToken(authentication);

        System.out.println(token);

        Map<String,String> response = new HashMap<>();

        response.put("token",token);

        // Generate a JWT token
        return ResponseEntity.ok(response);
    }
}
