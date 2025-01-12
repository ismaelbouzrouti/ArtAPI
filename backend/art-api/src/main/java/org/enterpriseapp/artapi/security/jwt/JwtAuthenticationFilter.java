package org.enterpriseapp.artapi.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.enterpriseapp.artapi.security.services.TokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Component
// Indicates that this class is a Spring component and will be automatically detected and instantiated.
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    // Constructor-based dependency injection of the `TokenService`.
    public JwtAuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    // Overrides the `doFilterInternal` method to implement custom filtering logic.
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Step 1: Retrieve the token from the request.
        String token = getTokenFromRequest(request);

        // Step 2: Check if the token is present and valid.
        if (token != null && tokenService.isTokenValid(token)) {
            // Step 3: Extract user details from the token and create authentication.
            Authentication authentication = getAuthentication(token);

            System.out.println("principal: " + authentication.getPrincipal());


            // Step 4: Set the authentication in the `SecurityContext`.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Step 5: Proceed with the filter chain (continue processing the request).
        filterChain.doFilter(request, response);
    }

    @Nullable
    // Helper method to extract the JWT token from the Authorization header.
    private String getTokenFromRequest(@NonNull HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        // Retrieves the `Authorization` header from the HTTP request.

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // Checks if the header exists and starts with "Bearer ".
            return bearerToken.substring(7); // Extracts the token part after "Bearer ".
        }
        return null; // If no valid token is found, return null.
    }

    @NotNull
    // Method to create an `Authentication` object based on the JWT token.
    private Authentication getAuthentication(String token) {
        String userName = tokenService.extractUsernameFromToken(token);
        // Extracts the username (principal) from the JWT token.

        // Converts roles (claims) extracted from the token into `GrantedAuthority`.
        Collection<? extends GrantedAuthority> authorities = tokenService.extractRolesFromToken(token).stream()
                .map(role -> new SimpleGrantedAuthority(role))
                // Maps each role string to a `SimpleGrantedAuthority` object.
                .toList();

        return new UsernamePasswordAuthenticationToken(userName, null, authorities);
        // Creates an `Authentication` object with the username, no credentials (null), and roles.
    }
}