package com.s14_maistorbg.service;

import com.s14_maistorbg.model.entities.User;
import com.s14_maistorbg.model.exceptions.NotFoundException;
import com.s14_maistorbg.model.repositories.CategoryRepository;
import com.s14_maistorbg.model.repositories.CityRepository;
import com.s14_maistorbg.model.repositories.CraftsManRepository;
import com.s14_maistorbg.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class AbstractService {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ModelMapper modelMapper;
    @Autowired
    protected CraftsManRepository craftsManRepository;
    @Autowired
    protected CityRepository cityRepository;
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected PasswordEncoder encoder;

    protected User getUserById(int userId){
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found!"));
    }
}
