package com.anyessglobal.cms_backend.service;

import com.anyessglobal.cms_backend.dto.AuthResponse;
import com.anyessglobal.cms_backend.dto.LoginRequest;
import com.anyessglobal.cms_backend.dto.RegisterRequest;
import com.anyessglobal.cms_backend.model.Role;
import com.anyessglobal.cms_backend.model.User;
import com.anyessglobal.cms_backend.repository.UserRepository;
import com.anyessglobal.cms_backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

//    public AuthResponse register(RegisterRequest request) {
//        // Check if user already exists
//        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
//            // In a real app, you'd throw a specific exception here
//            return AuthResponse.builder()
//                    .message("Email already in use")
//                    .build();
//        }
//
//        // Create new user
//        var user = User.builder()
//                .firstName(request.getFirstName())
//                .lastName(request.getLastName())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(Role.ROLE_USER) // Default to ROLE_USER
//                .build();
//
//        userRepository.save(user);
//
//        // Generate token
//        var jwtToken = jwtService.generateToken(user);
//
//        return AuthResponse.builder()
//                .token(jwtToken)
//                .message("User registered successfully")
//                .build();
//    }

    public AuthResponse register(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return AuthResponse.builder()
                    .message("Email already in use")
                    .build();
        }

        // Create new user with dynamic role selection
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                // 🔥 MODIFIED: Instead of hardcoded ROLE_USER, we use the role from the request
                // This allows you to create Admins directly from your new Dashboard page
                .role(Role.valueOf(request.getRole()))
                .build();

        userRepository.save(user);

        // Generate token
        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .message("User registered successfully")
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        // This will throw an exception if credentials are bad
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // If we get here, user is authenticated
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Generate token
        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .message("User logged in successfully")
                .build();
    }
}
