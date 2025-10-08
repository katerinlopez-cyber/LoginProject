package com.example.mvcapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Allow access to static resources (CSS, JS, images) without authentication
                .requestMatchers("/css/**", "/images/**", "/favicon.ico").permitAll()
                // Allow access to the login and signup pages without authentication
                .requestMatchers("/login", "/signup").permitAll()
                // Require authentication for any other request
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                // Specify the URL for our custom login page
                .loginPage("/login")
                // On successful login, redirect to the dashboard
                .defaultSuccessUrl("/dashboard", true)
                // On failed login, redirect back to the login page with an error parameter
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                // Allow anyone to log out
                .permitAll()
            );

        return http.build();
    }
}