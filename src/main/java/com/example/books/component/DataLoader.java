package com.example.books.component;

import com.example.books.entity.User;
import com.example.books.entity.enums.Role;
import com.example.books.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${spring.sql.init.mode}")
    String mode;


    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always")) {
            userRepository.save(User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("1234"))
                    .role(Role.ADMIN)
                    .build());
        }
    }
}
