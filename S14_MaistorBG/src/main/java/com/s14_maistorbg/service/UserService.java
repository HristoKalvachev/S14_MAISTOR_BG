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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractService {
    private final int craftsmanRoleId=2;

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
        cityRepository.findById(dto.getCityId()).orElseThrow(() -> new NotFoundException("City not found!"));
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        //Todo Make ROLEID NOT A MAGIC NUMBER
        if (user.getRole().getId() == craftsmanRoleId) {
            User craftsmanToAdd = userRepository.findByUsername(user.getUsername())
                    .orElseThrow(() -> new NotFoundException("User is not add!"));

            Craftsman craftsman = new Craftsman();
            Category category1 = categoryRepository.findById(dto.getRepairCategoryId()).orElseThrow(() -> new NotFoundException("Category not found!"));
            craftsman.setUserId(craftsmanToAdd.getId());
            craftsman.setCategory(category1);
            craftsManRepository.save(craftsman);
        }
        return modelMapper.map(user, UserWithoutPassDTO.class);
    }

    private void validateUserInformation(RegisterDTO dto) {
        //TODO TRY TO MAKE ONE QUERY
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new BadRequestException("The username exist!");
        }
        if (userRepository.findByPhoneNumber(dto.getPhoneNumber()).isPresent()){
            throw new BadRequestException("The phone number exist!");
        }
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("This email is already registered!");
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
        dto.setPosts(user.getMyOffers().stream().map(p -> modelMapper.map(p, OfferWithoutOwnerDTO.class)).collect(Collectors.toList()));
        return dto;
    }

    public String changePassword(ChangePasswordDTO dto, int id) {
        User user = getUserById(id);
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

    public String uploadProfilePhoto(int id, MultipartFile file) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found!"));
            String name = createFileAndReturnName(file);

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
