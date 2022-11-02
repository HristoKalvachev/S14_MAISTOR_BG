package com.s14_maistorbg.service;

import com.s14_maistorbg.model.entities.Category;
import com.s14_maistorbg.model.entities.Craftsman;
import com.s14_maistorbg.model.entities.Offer;
import com.s14_maistorbg.model.entities.User;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.exceptions.NotFoundException;
import com.s14_maistorbg.model.repositories.*;
import com.s14_maistorbg.utility.UserUtility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
    @Autowired
    protected PhotoCraftsmanRepository photoCraftsmanRepository;

    protected User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    protected Craftsman getCraftsmanById(int id) {
        return craftsManRepository.findById(id).orElseThrow(() -> new NotFoundException("Craftsman not found!"));
    }

    protected Offer getOfferById(int offerId) {
        return offerRepository.findById(offerId)
                .orElseThrow(() -> new NotFoundException("Offer does not exist"));
    }

    protected Category getCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category does not exist"));
    }

    public String createFileAndReturnName(MultipartFile file, int id) throws IOException {
        String ext = UserUtility.getFileExtension(file);
        String absolutePath = "images" + File.separator + System.nanoTime() + id + ext;
        String name = absolutePath.replace("images" + File.separator, "");
        File f = new File(absolutePath);
        if (!f.exists()) {
            Files.copy(file.getInputStream(), f.toPath());
        } else {
            throw new BadRequestException("This file already exists!");
        }
        return name;
    }

    protected Category getCategoryByType(String categoryType) {
        return categoryRepository.findByType(categoryType)
                .orElseThrow(() -> new NotFoundException("Category does not exist"));
    }

}
