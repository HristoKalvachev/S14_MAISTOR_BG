package com.s14_maistorbg.controller;


import com.s14_maistorbg.model.dto.craftsmanDTOs.RateCraftsManDTO;
import com.s14_maistorbg.model.dto.users.LoginDTO;
import com.s14_maistorbg.model.dto.users.RegisterDTO;
import com.s14_maistorbg.model.dto.users.UserWithoutPassDTO;

import com.s14_maistorbg.model.entities.User;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.exceptions.UnauthorizedException;
import com.s14_maistorbg.model.repositories.UserRepository;
import com.s14_maistorbg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.AbstractController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController

public class UserController extends ExceptionController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public UserWithoutPassDTO register(@RequestBody RegisterDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/auth")
    public UserWithoutPassDTO login(@RequestBody LoginDTO dto, HttpServletRequest request) {
        UserWithoutPassDTO result = userService.login(dto);
        if (result != null) {
            logUser(request, result.getId());
            return result;
//            public UserWithoutPassDTO login (@RequestBody LoginDTO dto, HttpSession session){
//                UserWithoutPassDTO result = userService.login(dto);
//                if (result != null) {
//                    session.setAttribute("LOGGED", true);
//                    session.setAttribute("USER_ID", result.getId());
//                    session.setAttribute("FIRST_NAME", result.getFirstName());
//                    session.setAttribute("LAST_NAME", result.getLastName());
//                    session.setAttribute("ROLE_ID", result.getRoleId());
//                    return result;
        } else {
            throw new BadRequestException("Wrong Credentials!");
        }
    }

    @GetMapping("/users/{userId}")
    public UserWithoutPassDTO getById(@PathVariable int userId) {
        return userService.getById(userId);
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
        System.out.println("You are successful logout! See you next time.");
    }

    @DeleteMapping("/users/{id}")
    public UserWithoutPassDTO delete(@PathVariable int id) {
        return userService.delete(id);
    }


    @PutMapping("/users/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseEntity<User> editAccount(@RequestBody User newUser, @PathVariable int id) {
        User updatedUser = userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword());
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setPhoneNumber(newUser.getPhoneNumber());
                    user.setProfilePicUrl(newUser.getProfilePicUrl());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new BadRequestException("The profile can not be edited!"));
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PostMapping("/users/rate/{id}")
    public RateCraftsManDTO rateCraftMan(@RequestBody RateCraftsManDTO dto, HttpSession session, @PathVariable int id) {


        int craftsmanRoleID = 1;
        if (userRepository.findById(id).get().getRoleId() != 1) {
            throw new UnauthorizedException("Craftsmen can`t rate other craftsman!");
        }
        return userService.rateCraftsman(id, dto.getRating());
    }
}
