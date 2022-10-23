package com.s14_maistorbg.model.dto.users;

import com.s14_maistorbg.model.entities.Category;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterDTO {

    private String username;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private int cityId;
    private LocalDate registeredAt;
    private String profilePicUrl;
    private int roleId;
    private int repairCategoryId;

}
