package com.example.mvcapp.service;

import com.example.mvcapp.model.Administrator;
import com.example.mvcapp.repository.AdministratorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AdminUserDetailsService.class);

    @Autowired
    private AdministratorRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Administrator admin = repository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.debug("User not found by username={}", username);
                    return new UsernameNotFoundException("User not found: " + username);
                });

        logger.debug("Loaded user: {} with role={} and passwordHashPrefix={}", admin.getUsername(), admin.getRole(), admin.getPassword() == null ? "null" : admin.getPassword().substring(0, Math.min(8, admin.getPassword().length())));

        // Map Administrator to Spring Security UserDetails
        return User.withUsername(admin.getUsername())
                .password(admin.getPassword())
                .roles(admin.getRole() == null ? "USER" : admin.getRole())
                .build();
    }
}
