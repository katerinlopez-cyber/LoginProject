package com.example.mvcapp.service;

import com.example.mvcapp.model.Administrator;
import com.example.mvcapp.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerAdmin(String username, String password, String fullName) {
        Administrator newAdmin = new Administrator();
        newAdmin.setUsername(username);
        // Hash the password before saving
        newAdmin.setPassword(passwordEncoder.encode(password));
        newAdmin.setFullName(fullName);
        // You can set a default role or get it from the form
        newAdmin.setRole("Moderator"); 
        
        administratorRepository.save(newAdmin);
    }
}
