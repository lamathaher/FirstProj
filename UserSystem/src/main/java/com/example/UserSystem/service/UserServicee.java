package com.example.UserSystem.service;

import com.example.UserSystem.model.User;
import com.example.UserSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServicee {

    private final UserRepository userRepository;

    @Autowired
    public UserServicee(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        System.out.println("عدد المستخدمين من الداتابيس: " + users.size());
        return users;
    }
}
