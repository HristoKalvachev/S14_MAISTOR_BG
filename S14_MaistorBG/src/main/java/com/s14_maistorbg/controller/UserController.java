package com.s14_maistorbg.controller;

import com.s14_maistorbg.model.dto.ExceptionDTO;
import com.s14_maistorbg.model.entities.User;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User register(@RequestBody User user) {
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getFirstName());
        System.out.println(user.getLastName());
        System.out.println(user.getPhoneNumber());
        System.out.println(user.getEmail());
        if (!isPhoneValid(user)) {
            throw new BadRequestException("Not valid phone number!");
        }
        userRepository.save(user);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> delete(User user, @PathVariable int id){

        User userForDelete = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("User does not exist!"));

        userRepository.delete(userForDelete);
        return new ResponseEntity<>(userForDelete, HttpStatus.OK);

    }


    @PutMapping("/users/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseEntity<User> editAccount(@RequestBody User newUser, @PathVariable int id) {
        User updatedUser = userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
//                    user.setPassword(newUser.getPassword());
//                    user.setFirstName(newUser.getFirstName());
//                    user.setLastName(newUser.getLastName());
//                    user.setPhoneNumber(newUser.getPhoneNumber());
//                    user.setProfilePicUrl(newUser.getProfilePicUrl());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new BadRequestException("The profile can not be edited!"));
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    private ExceptionDTO badRequestHandler(Exception exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setDateTime(LocalDateTime.now());
        exceptionDTO.setMsg(exception.getMessage());
        exceptionDTO.setStatus(HttpStatus.BAD_REQUEST.value());
        return exceptionDTO;
    }


    private boolean isPhoneValid(User u) {
        String pattern = "^([0|\\+[0-9]{10})";
        if (u.getPhoneNumber().equals(pattern)) {

            return true;
        }
        return false;
    }


}
