package com.s14_maistorbg.model.dto.userDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordDTO {

    private String password;
    private String newPassword;
    private String confirmNewPassword;

}
