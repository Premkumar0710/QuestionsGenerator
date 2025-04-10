package com.example.QuestionsGenerator.Controller;

import com.example.QuestionsGenerator.Entity.User;
import com.example.QuestionsGenerator.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}
