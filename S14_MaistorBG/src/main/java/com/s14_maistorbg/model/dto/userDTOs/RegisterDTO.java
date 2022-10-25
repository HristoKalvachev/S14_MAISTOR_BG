package com.s14_maistorbg.model.dto.userDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
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
