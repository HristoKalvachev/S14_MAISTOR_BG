package com.s14_maistorbg.service;


import com.s14_maistorbg.model.dto.offerDTOs.PostWithoutOwnerDTO;
import com.s14_maistorbg.model.dto.craftsmanDTOs.RateCraftsManDTO;
import com.s14_maistorbg.model.dto.users.*;
import com.s14_maistorbg.model.entities.Category;
import com.s14_maistorbg.model.entities.Craftsman;
import com.s14_maistorbg.model.entities.User;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.exceptions.NotFoundException;
import com.s14_maistorbg.model.exceptions.UnauthorizedException;
import com.s14_maistorbg.model.repositories.CategoryRepository;
import com.s14_maistorbg.model.repositories.CityRepository;
import com.s14_maistorbg.model.repositories.CraftsManRepository;
import com.s14_maistorbg.model.repositories.UserRepository;
import com.s14_maistorbg.utility.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CraftsManRepository craftsManRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder encoder;

    public UserWithoutPassDTO login(LoginDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        if (!validateUsername(username) || !validatePassword(password)) {
            throw new BadRequestException("The fields are mandatory!");
        }
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            User u = user.get();
            if (encoder.matches(password, u.getPassword())) {
                return modelMapper.map(user.get(), UserWithoutPassDTO.class);
            } else {
                throw new UnauthorizedException("Wrong credentials!");
            }
        } else {
            throw new UnauthorizedException("Wrong credentials!");
        }
    }

    private boolean validatePassword(String password) {
        if (password == null || password.isBlank()) {
            return false;
        }
        return true;
    }

    private boolean validateUsername(String username) {
        if (username == null || username.isBlank()) {
            return false;
        }
        return true;
    }

    public UserWithoutPassDTO register(RegisterDTO dto) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new BadRequestException("The username exist!");
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new BadRequestException("Passwords mismatch!");
        }
        if (!UserUtility.isEmailValid(dto.getEmail())) {
            throw new BadRequestException("Invalid email!");
        }
        if (!UserUtility.isPassValid(dto.getPassword())) {
            throw new BadRequestException("Invalid password!");
        }
        if (!UserUtility.isPhoneValid(dto.getPhoneNumber())) {
            throw new BadRequestException("Invalid phone number!");
        }
        User user = modelMapper.map(dto, User.class);
//        City city = cityRepository.findByCityId(dto.getCityId()).orElseThrow(()-> new NotFoundException("City not found"));
        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println(user.getCity());
        userRepository.save(user);
        if (user.getRole().getId() == 2) {
            User craftsmanToAdd = userRepository.findByUsername(user.getUsername())
                    .orElseThrow(() -> new NotFoundException("User is not add!"));

            Craftsman craftsman = new Craftsman();
            Category category = new Category();
            category.setId(dto.getRepairCategoryId());
            Category category1 = categoryRepository.findById(dto.getRepairCategoryId()).orElseThrow(()-> new NotFoundException("Category not found!"));
            category.setType(category1.getType());
            craftsman.setUserId(craftsmanToAdd.getId());
            craftsman.setRating(0);
            craftsman.setNumberUsersRated(0);
            craftsman.setCategory(category);
            craftsManRepository.save(craftsman);
        }
        return modelMapper.map(user, UserWithoutPassDTO.class);
    }


    public EditUserDTO editAccount(EditUserDTO newUser, int id) {
        if (!UserUtility.isEmailValid(newUser.getEmail())) {
            throw new BadRequestException("Invalid email!");
        }
        if (!UserUtility.isPhoneValid(newUser.getPhoneNumber())) {
            throw new BadRequestException("Invalid phone number!");
        }
        Optional<User> editedUser = userRepository.findById(id);
        EditUserDTO dto = modelMapper.map(editedUser, EditUserDTO.class);
        dto.setUsername(newUser.getUsername());
        dto.setFirstName(newUser.getFirstName());
        dto.setLastName(newUser.getLastName());
        dto.setPhoneNumber(newUser.getPhoneNumber());
        dto.setProfilePicUrl(newUser.getProfilePicUrl());
        userRepository.save(modelMapper.map(dto, User.class));
        return dto;
    }

    public UserWithoutPassDTO delete(int id) {
        User userForDelete = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("User does not exist!"));
        userRepository.delete(userForDelete);
        return modelMapper.map(userForDelete, UserWithoutPassDTO.class);
    }

    public UserWithoutPassDTO getById(int userId) {
        User user = getUserById(userId);
        UserWithoutPassDTO dto = modelMapper.map(user, UserWithoutPassDTO.class);
        dto.setPosts(user.getMyOffers().stream().map(p -> modelMapper.map(p, PostWithoutOwnerDTO.class)).collect(Collectors.toList()));
        return dto;
    }


    public RateCraftsManDTO rateCraftsman(int id, double rate) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found!"));
        if (rate < 1 || rate > 10) {
            throw new BadRequestException("Rate must be between 1 and 10!");
        }
        Craftsman craftsman = craftsManRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("Craftsman not found!"));
        int currentTotalRateSum = craftsman.getRating();
        int peopleRated = craftsman.getNumberUsersRated();
        currentTotalRateSum += rate;
        peopleRated += 1;
        craftsman.setRating(currentTotalRateSum);
        craftsman.setNumberUsersRated(peopleRated);
        double rating = (double) currentTotalRateSum / peopleRated;
        RateCraftsManDTO rateCraftsManDTO = new RateCraftsManDTO();
        rateCraftsManDTO.setRating(rating);
        rateCraftsManDTO.setUsername(user.getUsername());
        craftsManRepository.save(craftsman);
        return rateCraftsManDTO;
    }

    public String changePassword(ChangePasswordDTO dto, int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("No user found!"));
        if (!dto.getPassword().equals(user.getPassword())) {
            throw new BadRequestException("Incorrect password!");
        }
        if (!UserUtility.isPassValid(dto.getPassword())) {
            throw new BadRequestException("Password is weak!");
        }
        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new BadRequestException("Password mismatch!");
        }
        user.setPassword(dto.getNewPassword());
        userRepository.save(user);

        return "Password changed successfully!";
    }
}
