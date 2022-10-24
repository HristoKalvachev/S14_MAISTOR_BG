package com.s14_maistorbg.service;

import com.s14_maistorbg.model.entities.User;
import com.s14_maistorbg.model.exceptions.NotFoundException;
import com.s14_maistorbg.model.repositories.*;
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
    protected RateRepository rateRepository;
    @Autowired
    protected PasswordEncoder encoder;
    @Autowired
    protected CommentRepository commentRepository;

    protected User getUserById(int userId){
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found!"));
    }
}
