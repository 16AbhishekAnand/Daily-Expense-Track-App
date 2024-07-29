package com.example.account.demo.service;

import com.example.account.demo.model.Users;
import com.example.account.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    // create user
    public Users createUser(Users users) {
        if(users.getEmail()==null || users.getName()==null || users.getMobileNumber()==null) {
            throw new IllegalArgumentException("All fields are Mandatory to fill.");
        }
        return userRepository.save(users);
    }

    // retrieve user by id.
    public Users getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    //retrieve all users
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }
}
