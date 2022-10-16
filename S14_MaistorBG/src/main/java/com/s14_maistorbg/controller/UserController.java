package com.s14_maistorbg.controller;

import com.s14_maistorbg.entities.User;
import com.s14_maistorbg.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User register(@RequestBody User user){
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getFirstName());
        System.out.println(user.getLastName());
        System.out.println(user.getPhoneNumber());
        System.out.println(user.getEmail());
        userRepository.save(user);
        return user;
    }

    public User login(@RequestBody User user){
        userRepository.
    }


}
