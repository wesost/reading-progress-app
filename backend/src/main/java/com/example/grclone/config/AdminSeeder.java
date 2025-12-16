package com.example.grclone.config;

import com.example.grclone.entities.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.grclone.repositories.UserRepository;

@Configuration
public class AdminSeeder {
    
    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("adminpassword"));
                admin.setRole("ROLE_ADMIN");
                admin.setVerified(true);
                userRepository.save(admin);
                System.out.println("Admin created: username=admin, password=adminpassword");
            }
        };
    }
}
