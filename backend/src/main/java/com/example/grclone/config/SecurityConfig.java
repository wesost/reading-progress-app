package com.example.grclone.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // read into csrf
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/api/home").permitAll()
                .requestMatchers("api/books/**", "api/login/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "api/books/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.DELETE, "api/reviews/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "api/reviews/**").permitAll()
                .requestMatchers(HttpMethod.PATCH, "api/reviews/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "api/users").permitAll()
                .requestMatchers(HttpMethod.GET, "api/users/me").permitAll()
                .requestMatchers(HttpMethod.GET, "api/users/verify/**").permitAll()
                .requestMatchers(HttpMethod.GET, "api/users/search/**").permitAll()
                .requestMatchers(HttpMethod.GET, "api/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "api/users/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessHandler((_, response, _) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"message\":\"Logout successful\"}");
                })
                .permitAll()
            );
        return http.build();
    }

    // Authenticationmanager (as used in AuthControlller) is not automatically available as a bean, so exposing it here TODO: verify this is true...
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
}
