package com.agromarketplace.service;

import com.agromarketplace.dto.AuthDtos;
import com.agromarketplace.entity.User;
import com.agromarketplace.exception.BusinessException;
import com.agromarketplace.repository.UserRepository;
import com.agromarketplace.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthDtos.AuthResponse register(AuthDtos.RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) throw new BusinessException("Username already taken");
        if (userRepository.existsByEmail(request.email())) throw new BusinessException("Email already taken");

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role());
        userRepository.save(user);

        return new AuthDtos.AuthResponse(jwtService.generateToken(user.getUsername(), user.getRole().name()));
    }

    public AuthDtos.AuthResponse login(AuthDtos.LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        User user = userRepository.findByUsername(request.username()).orElseThrow();
        return new AuthDtos.AuthResponse(jwtService.generateToken(user.getUsername(), user.getRole().name()));
    }
}
