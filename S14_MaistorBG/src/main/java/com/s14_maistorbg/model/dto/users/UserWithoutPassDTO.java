package com.s14_maistorbg.model.dto.users;

import lombok.Data;

@Data
public class UserWithoutPassDTO {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private int roleId;
}
