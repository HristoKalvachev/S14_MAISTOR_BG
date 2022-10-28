package com.s14_maistorbg.service;

import com.s14_maistorbg.model.entities.Craftsman;
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
    @Autowired
    protected PhotoOfferRepository photoOfferRepository;
    @Autowired
    protected OfferRepository offerRepository;
    @Autowired
    protected ApplicationForOfferRepository applicationForOfferRepository;

    protected User getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found!"));
    }
    protected Craftsman getCraftsmanById(int id){
        return craftsManRepository.findById(id).orElseThrow(()-> new NotFoundException("Craftsman not found!"));
    }

}
