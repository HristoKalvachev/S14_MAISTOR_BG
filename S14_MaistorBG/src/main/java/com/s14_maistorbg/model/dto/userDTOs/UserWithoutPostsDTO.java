package com.s14_maistorbg.model.dto.userDTOs;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserWithoutPostsDTO {

    private int id;
    private String username;
    private String firstName;
    private String lastName;
}
