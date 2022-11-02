package com.s14_maistorbg.controller;


import com.s14_maistorbg.model.dto.userDTOs.*;

import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserWithoutPassDTO register(@RequestBody RegisterDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/auth")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public UserWithoutPostsDTO login(@RequestBody LoginDTO dto, HttpServletRequest request) {
        UserWithoutPostsDTO result = userService.login(dto);
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
    public String logout(HttpSession session) {
        session.invalidate();
        return "You are successfully logout!";
    }

    @DeleteMapping("/users")
    @ResponseStatus(code = HttpStatus.OK)
    public UserWithoutPassDTO deleteProfile(HttpServletRequest request) {
        int userId = getLoggedUserId(request);
        return userService.deleteProfile(userId);
    }

    @PutMapping("/users")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseEntity<EditUserDTO> editAccount(@RequestBody EditUserDTO newUser, HttpServletRequest request) {
        int userId = getLoggedUserId(request);
        return ResponseEntity.ok(userService.editAccount(newUser, userId));
    }

    @PutMapping("/users/change_pass/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public String changePassword(@RequestBody ChangePasswordDTO dto, @PathVariable int id,HttpServletRequest req) {
        getLoggedUserId(req);
        return userService.changePassword(dto, id);
    }

    @PostMapping("/users/{id}/photo")
    @ResponseStatus(code = HttpStatus.OK)
    public String uploadProfilePhoto(@PathVariable int id, @RequestParam(value = "file") MultipartFile file) {
        return userService.uploadProfilePhoto(id, file);
    }
}
