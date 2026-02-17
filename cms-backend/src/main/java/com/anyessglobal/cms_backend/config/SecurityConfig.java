//package com.anyessglobal.cms_backend.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.Customizer; // Make sure this is imported
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final JwtAuthFilter jwtAuthFilter;
//    private final AuthenticationProvider authenticationProvider;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                .cors(Customizer.withDefaults()) // Use the 'corsConfigurationSource' bean
//                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF
//
//                // This is the corrected, modern, lambda-based syntax
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/auth/**").permitAll()
//                        .requestMatchers("/h2-console/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/categories", "/api/categories/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/products", "/api/products/**").permitAll()
//                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Allow preflight OPTIONS requests
//                        .anyRequest().authenticated()
//                )
//
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//
//                .headers(headers ->
//                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable) // Fix for H2 console
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        // This is the correct setting for testing with Postman and your Vite app
//        configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:3000"));
//        // Or, for development, just allow everything:
//        // configuration.setAllowedOrigins(List.of("*"));
//
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}
//package com.anyessglobal.cms_backend.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final JwtAuthFilter jwtAuthFilter;
//    private final AuthenticationProvider authenticationProvider;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                .cors(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//
//                .authorizeHttpRequests(auth -> auth
//                        // 1. PUBLIC ENDPOINTS for Main Website (GET allowed without authentication)
//                        .requestMatchers(HttpMethod.GET, "/api/public/**").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/public/contact").permitAll()
//
//                        // 2. AUTHENTICATION ENDPOINTS
//                        .requestMatchers("/api/auth/login").permitAll() // Login must be open to everyone
//                        // 🔒 RESTRICTED REGISTRATION: Only Admin or existing Users can create new accounts
//                        .requestMatchers("/api/auth/register").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
//
//                        // 3. UTILITY ENDPOINTS (H2 Console, Options)
//                        .requestMatchers("/h2-console/**").permitAll()
//                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//
//                        // 4. SECURE ADMIN ENDPOINTS (Categories/Products require login)
//                        .requestMatchers("/api/categories/**").authenticated()
//                        .requestMatchers("/api/products/**").authenticated()
//
//                        // 5. Everything else requires authentication
//                        .anyRequest().authenticated()
//                )
//
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//
//                .headers(headers ->
//                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        configuration.setAllowedOrigins(List.of(
//                "http://localhost:5173",          // Your Admin Panel (Vite Default)
//                "http://127.0.0.1:5500",          // Local Live Server
//                "https://anyessglobal.vercel.app" // Your Production Website
//        ));
//
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}

package com.anyessglobal.cms_backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF (Standard for stateless JWT APIs)
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Enable CORS with our custom rules below
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 3. Define URL Access Rules
                .authorizeHttpRequests(req -> req
                        // Public: Login & Register
                        .requestMatchers("/api/auth/**").permitAll()

                        // Public: GET requests for Categories & Products (for your website)
                        .requestMatchers(HttpMethod.GET, "/api/public/**").permitAll()

                        // Public: POST for Contact Form (Visitor Messages)
                        .requestMatchers(HttpMethod.POST, "/api/public/contact").permitAll()

                        // Protected: Everything else (Dashboard, Leads, Delete actions)
                        .anyRequest().authenticated()
                )

                // 4. Stateless Session (No cookies)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 🔒 ALLOWED ORIGINS (Who can talk to this backend?)
        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173",          // Local React Dashboard
                "http://127.0.0.1:5173",          // Localhost Alternative
                "https://anyessglobal.vercel.app", // 🚀 YOUR PRODUCTION SITE
                "https://anyessglobal.vercel.app/" // (Just in case of trailing slash)
        ));

        // Allowed HTTP Methods
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allowed Headers (Authorization is critical for the Dashboard login)
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));

        // Allow credentials (needed if you ever add cookies later, good practice to keep true)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}