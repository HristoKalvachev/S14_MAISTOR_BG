package com.s14_maistorbg.model.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditUserDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private int cityId;
    private String profilePicUrl;

}
