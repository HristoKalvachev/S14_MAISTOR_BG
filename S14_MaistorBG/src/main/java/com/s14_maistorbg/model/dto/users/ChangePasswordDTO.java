package com.s14_maistorbg.model.dto.users;

import lombok.Data;

@Data
public class ChangePasswordDTO {

    private String password;
    private String newPassword;
    private String confirmNewPassword;

}
