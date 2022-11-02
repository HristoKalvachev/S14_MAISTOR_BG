package com.s14_maistorbg.service;


import com.s14_maistorbg.model.dto.offerDTOs.OfferWithoutOwnerDTO;
import com.s14_maistorbg.model.dto.userDTOs.*;
import com.s14_maistorbg.model.entities.Category;
import com.s14_maistorbg.model.entities.Craftsman;
import com.s14_maistorbg.model.entities.User;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.exceptions.NotFoundException;
import com.s14_maistorbg.model.exceptions.UnauthorizedException;
import com.s14_maistorbg.utility.UserUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractService {
    private static final int CRAFTSMAN_ROLE_ID = 2;

    public UserWithoutPostsDTO login(LoginDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        if (!validateUsername(username) || !validatePassword(password)) {
            throw new BadRequestException("The fields are mandatory!");
        }
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            User u = user.get();
            if (encoder.matches(password, u.getPassword())) {
                return modelMapper.map(user.get(), UserWithoutPostsDTO.class);
            } else {
                throw new UnauthorizedException("Wrong credentials!");
            }
        } else {
            throw new UnauthorizedException("Wrong credentials!");
        }
    }

    private boolean validatePassword(String password) {
        return password != null && !password.isBlank();
    }

    private boolean validateUsername(String username) {
        return username != null && !username.isBlank();
    }

    @Transactional
    public UserWithoutPassDTO register(RegisterDTO dto) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        validateUserInformation(dto);
        User user = modelMapper.map(dto, User.class);
        cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new NotFoundException("City not found!"));
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRegisteredAt(LocalDateTime.now());
        userRepository.save(user);
        if (user.getRole().getId() == CRAFTSMAN_ROLE_ID) {
            User craftsmanToAdd = userRepository.findByUsername(user.getUsername())
                    .orElseThrow(() -> new NotFoundException("User is not added!"));
            Category category1 = getCategoryById(dto.getRepairCategoryId());
            Craftsman craftsman = new Craftsman();
            craftsman.setUserId(craftsmanToAdd.getId());
            craftsman.setCategory(category1);
            craftsman.setMyCategories(new ArrayList<>());
            craftsman.getMyCategories().add(category1);
            craftsManRepository.save(craftsman);
        }
        return modelMapper.map(user, UserWithoutPassDTO.class);
    }

    private void validateUserInformation(RegisterDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new BadRequestException("The username exist!");
        }
        if (userRepository.findByPhoneNumber(dto.getPhoneNumber()).isPresent()){
            throw new BadRequestException("The phone number exist!");
        }
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("This email is already registered!");
        }
        if (!UserUtility.isUsernameValid(dto.getUsername())){
            throw new BadRequestException("Invalid username!");
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
    }

    public EditUserDTO editAccount(EditUserDTO newUser, int id) {
        if (!UserUtility.isEmailValid(newUser.getEmail())) {
            throw new BadRequestException("Invalid email!");
        }
        if (!UserUtility.isPhoneValid(newUser.getPhoneNumber())) {
            throw new BadRequestException("Invalid phone number!");
        }
        User editedUser = getUserById(id);
        editedUser.setUsername(newUser.getUsername());
        editedUser.setFirstName(newUser.getFirstName());
        editedUser.setLastName(newUser.getLastName());
        editedUser.setPhoneNumber(newUser.getPhoneNumber());
        editedUser.setProfilePicUrl(newUser.getProfilePicUrl());
        editedUser.setEmail(newUser.getEmail());
        userRepository.save(editedUser);
        return modelMapper.map(editedUser, EditUserDTO.class);
    }

    public UserWithoutPassDTO deleteProfile(int id) {
        User userForDelete = getUserById(id);
        userForDelete.setUsername("Deleted at " + LocalDateTime.now());
        userForDelete.setFirstName("Deleted at " + LocalDateTime.now());
        userForDelete.setLastName("Deleted at " + LocalDateTime.now());
        userForDelete.setPassword("Deleted at " + LocalDateTime.now());
        userForDelete.setEmail("Deleted at " + LocalDateTime.now());
        userForDelete.setPhoneNumber("Deleted at " + LocalDateTime.now());
        userForDelete.setProfilePicUrl("Deleted at " + LocalDateTime.now());
        userRepository.save(userForDelete);
        return modelMapper.map(userForDelete, UserWithoutPassDTO.class);
    }

    public UserWithoutPassDTO getById(int userId) {
        User user = getUserById(userId);
        UserWithoutPassDTO dto = modelMapper.map(user, UserWithoutPassDTO.class);
        dto.setPosts(user.getMyOffers().stream()
                .map(p -> modelMapper.map(p, OfferWithoutOwnerDTO.class))
                .collect(Collectors.toList()));
        return dto;
    }

    public String changePassword(ChangePasswordDTO dto, int id) {
        User user = getUserById(id);
        boolean isPassCorrect = encoder.matches(dto.getPassword(), user.getPassword());
        if (!isPassCorrect) {
            throw new BadRequestException("Incorrect password!");
        }
        if (!UserUtility.isPassValid(dto.getPassword())) {
            throw new BadRequestException("Password is weak!");
        }
        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new BadRequestException("Password mismatch!");
        }
        user.setPassword(encoder.encode(dto.getNewPassword()));
        userRepository.save(user);
        return "Password changed successfully!";
    }

    public String uploadProfilePhoto(int id, MultipartFile file) {
        try {
            User user = getUserById(id);
            String name = createFileAndReturnName(file, id);

            if(user.getProfilePicUrl() != null){
                File oldProfilePic = new File(user.getProfilePicUrl());
                oldProfilePic.delete();
            }
            user.setProfilePicUrl(name);
            userRepository.save(user);
            return name;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), e);
        }
    }
}
