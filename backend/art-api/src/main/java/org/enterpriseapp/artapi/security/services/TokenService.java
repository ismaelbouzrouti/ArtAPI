package org.enterpriseapp.artapi.security.services;

import io.jsonwebtoken.Claims;
import org.enterpriseapp.artapi.users.User;
import org.enterpriseapp.artapi.users.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TokenService {


    private final JwtEncoder encoder;
    private final JwtDecoder decoder;
    private final UserRepository userRepository;

    public TokenService(JwtEncoder encoder, JwtDecoder decoder, UserRepository userRepository){

        this.encoder = encoder;
        this.decoder = decoder;
        this.userRepository = userRepository;
    }


    // Generates a JWT token for the authenticated user.
    public String generateToken(Authentication authentication) {
        System.out.println("in generate token");
        // Debugging statement to indicate token generation.

        Instant now = Instant.now();
        // Gets the current timestamp.

        User user = (User) authentication.getPrincipal();
        // Retrieves the authenticated user from the `Authentication` object.

        // Retrieves roles/authorities assigned to the user.
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        // Builds the claims for the JWT.
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self") // Sets the issuer of the token.
                .subject(user.getId().toString()) // Sets the subject to the user's ID.
                .issuedAt(now) // Sets the token's issuance time.
                .expiresAt(now.plus(1, ChronoUnit.HOURS)) // Sets expiration time to 1 hour from now.
                .claim("isAdmin", user.getIsAdmin()) // Adds custom claim indicating admin status.
                .claim("roles", authorities.stream()
                        .map(GrantedAuthority::getAuthority) // Extracts role names.
                        .toList())
                .build();

        // Prepares parameters for the JWT encoder with HMAC SHA-256 signing.
        var encoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);

        return this.encoder.encode(encoderParameters).getTokenValue();
        // Encodes the claims and returns the JWT token as a string.
    }

    // Validates a given JWT token.
    public boolean isTokenValid(String token) {
        try {
            Jwt jwt = decoder.decode(token);
            // Decodes the token. If successful, the token is valid.

            return true;
        } catch (JwtValidationException e) {
            // Catches validation exceptions if the token is invalid.
            System.out.println("Token validation failed: " + e.getMessage());
            return false; // Indicates an invalid token.
        }
    }

    // Extracts the username from the JWT token.
    public String extractUsernameFromToken(String token) {
        Jwt jwt = decoder.decode(token);
        // Decodes the token.

        String userId = jwt.getSubject();
        // Extracts the subject (user ID) from the token.

        Optional<User> optionalUser = userRepository.findById(Long.valueOf(userId));
        // Looks up the user in the repository by ID.

        if (optionalUser.isPresent()) {
            return optionalUser.get().getUsername();
            // Returns the username if the user exists.
        } else {
            throw new UsernameNotFoundException("user was not found");
            // Throws an exception if the user is not found.
        }
    }

    // Extracts roles from the JWT token.
    public List<String> extractRolesFromToken(String token) {
        Jwt jwt = decoder.decode(token);
        // Decodes the token.

        return (List<String>) jwt.getClaims().get("roles");
        // Retrieves and returns the roles from the token's claims.
    }
}