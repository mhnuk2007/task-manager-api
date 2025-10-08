package com.learning.taskmanagerapi.service;

import com.learning.taskmanagerapi.dto.AuthResponse;
import com.learning.taskmanagerapi.dto.LoginRequest;
import com.learning.taskmanagerapi.dto.RegisterRequest;
import com.learning.taskmanagerapi.entity.Role;
import com.learning.taskmanagerapi.entity.User;
import com.learning.taskmanagerapi.repository.UserRepository;
import com.learning.taskmanagerapi.security.JwtUtil;
import com.learning.taskmanagerapi.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    public String register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return "Username already exists";
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);

        return "User registered successfully";
    }

    public String registerAdmin(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return "Username already exists";
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(Role.ROLE_ADMIN);
        userRepository.save(user);

        return "Admin registered successfully";
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword())
        );
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Username not found"));

        String token = jwtUtil.generateToken(new UserDetailsImpl(user));

        AuthResponse authResponse = new AuthResponse(token, user.getId(), user.getUsername(), user.getRole().name());

        return authResponse;
        }
}
