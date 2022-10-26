package com.s14_maistorbg.controller;


import com.s14_maistorbg.model.dto.userDTOs.*;

import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.repositories.UserRepository;
import com.s14_maistorbg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController

public class UserController extends ExceptionController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserWithoutPassDTO register(@RequestBody RegisterDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/auth")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public UserWithoutPassDTO login(@RequestBody LoginDTO dto, HttpServletRequest request) {
        UserWithoutPassDTO result = userService.login(dto);
        if (result != null) {
            logUser(request, result.getId());
            return result;
        } else {
            throw new BadRequestException("Wrong Credentials!");
        }
    }

    @GetMapping("/users/{userId}")
    @ResponseStatus(code = HttpStatus.OK)
    public UserWithoutPassDTO getById(@PathVariable int userId) {
        return userService.getById(userId);
    }

    @PostMapping("/logout")
    @ResponseStatus(code = HttpStatus.OK)
    public void logout(HttpSession session) {
        session.invalidate();
        System.out.println("You are successful logout! See you next time.");
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public UserWithoutPassDTO delete(@PathVariable int id) {
        return userService.delete(id);
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseEntity<EditUserDTO> editAccount(@RequestBody EditUserDTO newUser, HttpServletRequest request) {
        getLoggedUserId(request);
        return ResponseEntity.ok(userService.editAccount(newUser, Integer.parseInt(request.getSession().getId())));
    }

    @PutMapping("/users/edit/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public String changePassword(@RequestBody ChangePasswordDTO dto, @PathVariable int id) {
        return userService.changePassword(dto, id);
    }

    @PostMapping("/users/{id}/photo")
    @ResponseStatus(code = HttpStatus.OK)
    public String uploadProfilePhoto(@PathVariable int id, @RequestParam(value = "file") MultipartFile file) {
        return userService.uploadProfilePhoto(id,file);
    }
}
