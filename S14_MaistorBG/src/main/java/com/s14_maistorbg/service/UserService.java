package com.s14_maistorbg.service;

import com.s14_maistorbg.model.dto.users.LoginDTO;
import com.s14_maistorbg.model.dto.users.RegisterDTO;
import com.s14_maistorbg.model.dto.users.UserWithoutPassDTO;
import com.s14_maistorbg.model.entities.User;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.exceptions.UnauthorizedException;
import com.s14_maistorbg.model.repositories.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    public UserWithoutPassDTO login(LoginDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        if (user.isPresent()){
            return modelMapper.map(user.get(), UserWithoutPassDTO.class);
        }else {
            throw new UnauthorizedException("Wrong credentials!");
        }
    }

    public UserWithoutPassDTO register(RegisterDTO dto){
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        if (userRepository.findByUsername(dto.getUsername()).isPresent()){
            throw new BadRequestException("The username exist!");
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())){
            throw new BadRequestException("Passwords mismatch!");
        }
        if(!isEmailValid(dto)){
            throw new BadRequestException("Invalid email!");
        }
        if (!isPassValid(dto)){
            throw new BadRequestException("Invalid password!");
        }
        if (!isPhoneValid(dto)){
            throw new BadRequestException("Invalid phone number!");
        }
        User user = modelMapper.map(dto, User.class);
        userRepository.save(user);
        return modelMapper.map(user, UserWithoutPassDTO.class);
    }

    private boolean isPhoneValid(RegisterDTO user) {
        Pattern p = Pattern.compile("0[0-9]{9}");
        Matcher m = p.matcher(user.getPhoneNumber());
        boolean hasMatch = m.matches();
        if (hasMatch) {
            return true;
        }
        return false;
    }

    private boolean isEmailValid(RegisterDTO dto){
        return EmailValidator.getInstance(true).isValid(dto.getEmail());
    }

    private boolean isPassValid(RegisterDTO dto){
        /*
        Must have at least one numeric character
        Must have at least one lowercase character
        Must have at least one uppercase character
        Must have at least one special symbol among @#$%
        Password length should be between 8 and 20
         */
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(dto.getPassword());
        boolean hasMatch = m.matches();
        if (hasMatch){
            return  true;
        }
        return false;
    }

    public UserWithoutPassDTO delete(int id) {
        User userForDelete = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("User does not exist!"));
        userRepository.delete(userForDelete);
        return modelMapper.map(userForDelete, UserWithoutPassDTO.class);
    }
}
