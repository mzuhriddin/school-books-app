package com.example.books.controller;

import com.example.books.dto.LoginDto;
import com.example.books.dto.RegisterDto;
import com.example.books.entity.User;
import com.example.books.entity.enums.Role;
import com.example.books.repository.UserRepository;
import com.example.books.security.JwtProvider;
import com.example.books.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public record AuthController(AuthService authService, JwtProvider jwtProvider,
                             AuthenticationManager authenticationManager, UserRepository userRepository,
                             PasswordEncoder passwordEncoder) {
    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginDto loginDto) {
        if (authService.loadUserByUsername(loginDto.getUsername()) == null) {
            return ResponseEntity.badRequest().build();
        }
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(loginDto.getUsername());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterDto registerDto) {
        Optional<User> byUsername = userRepository.findByUsername(registerDto.getUsername());
        if (byUsername.isPresent()) {
            return ResponseEntity.status(400).body("This username already exist");
        }
        userRepository.save(User.builder()
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .username(registerDto.getUsername())
                .phoneNumber(registerDto.getPhoneNumber())
                .role(Role.USER)
                .build());
        return ResponseEntity.ok("Successfully registered.\nGo to login page.");
    }
}
