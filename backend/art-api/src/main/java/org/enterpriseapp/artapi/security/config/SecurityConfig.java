package org.enterpriseapp.artapi.security.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.enterpriseapp.artapi.security.jwt.JwtAuthenticationFilter;
import org.enterpriseapp.artapi.security.services.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;

import java.util.*;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{

    @Value("${SECRET_KEY}")
    private String jwtKey;

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }


    @Bean
    // Declares a bean for the security filter chain.
    public SecurityFilterChain securityFilterChain(HttpSecurity http, TokenService tokenService) throws Exception {
        // Configures the HTTP security settings for the application.

        return http
                .cors(c -> c.configurationSource(configurationSource()))
                // Enables and configures CORS with a custom configuration source.

                .csrf(csrf -> csrf.disable())
                // Disables CSRF protection as JWT is used instead.

                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/login").permitAll()
                                // Allows anyone to access the `/login` endpoint.

                                .requestMatchers("/signup").permitAll()
                                // Allows anyone to access the `/signup` endpoint.

                                .requestMatchers("/admin").hasRole("admin")
                                // Restricts `/admin` access to users with the "admin" role.

                                .anyRequest().authenticated()
                        // Requires authentication for any other requests.
                )

                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
                // Configures the app as an OAuth2 resource server, validating JWT tokens.

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configures stateless session management (no sessions are created).

                .httpBasic(withDefaults())
                // Enables HTTP Basic authentication as a fallback.

                .addFilterBefore(new JwtAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class)
                // Adds a custom filter for JWT authentication before the username/password filter.

                .build();
        // Builds and returns the configured security filter chain.
    }

    @Bean
        // Declares a bean for the `AuthenticationManager`, which manages authentication processes.
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        System.out.println("inside authentication manager");
        // Debug log to indicate when the authentication manager is initialized.

        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        // Retrieves a shared `AuthenticationManagerBuilder` instance from the `HttpSecurity` context.

        authenticationManagerBuilder.userDetailsService(userDetailsService)
                // Configures the `AuthenticationManager` to use the custom `UserDetailsService`.

                .passwordEncoder(passwordEncoder());
        // Configures the `AuthenticationManager` to use the `BCryptPasswordEncoder`.

        return authenticationManagerBuilder.build();
        // Builds and returns the configured `AuthenticationManager`.
    }

    @Bean
    // Declares a bean for configuring CORS settings.
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Creates a new instance of `CorsConfiguration`.

        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        // Specifies allowed origins for cross-origin requests.

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        // Specifies allowed HTTP methods.

        configuration.setAllowCredentials(true);
        // Allows credentials (e.g., cookies, headers) in cross-origin requests.

        configuration.setAllowedHeaders(List.of("*"));
        // Allows all headers in cross-origin requests.

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Creates a CORS configuration source based on URL paths.

        source.registerCorsConfiguration("/**", configuration);
        // Registers the configuration for all paths.

        return source;
        // Returns the configured `CorsConfigurationSource`.
    }

    @Bean
    // Declares a bean for the password encoder.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // Returns a BCrypt-based password encoder for hashing passwords.
    }

    @Bean
        // Declares a bean for JWT encoding.
    JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(jwtKey.getBytes()));
        // Configures and returns a JWT encoder with the secret key.
    }

    @Bean
    // Declares a bean for JWT decoding.
    public JwtDecoder jwtDecoder() {
        byte[] bytes = jwtKey.getBytes();
        // Converts the secret key to a byte array.

        SecretKeySpec originalKey = new SecretKeySpec(bytes, 0, bytes.length, "RSA");
        // Creates a `SecretKeySpec` for cryptographic operations.

        return NimbusJwtDecoder.withSecretKey(originalKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
        // Configures and returns a JWT decoder with the HS256 algorithm.
    }
}


