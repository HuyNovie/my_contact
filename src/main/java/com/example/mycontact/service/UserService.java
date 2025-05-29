package com.example.mycontact.service;

import com.example.mycontact.dto.RegisterDTO;
import com.example.mycontact.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void save(RegisterDTO registerDTO);

    List<User> getAll();

    User findByEmail(String email);
}
