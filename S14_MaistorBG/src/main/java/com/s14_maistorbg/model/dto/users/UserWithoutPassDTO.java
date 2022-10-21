package com.s14_maistorbg.model.dto.users;

import com.s14_maistorbg.model.dto.offerDTOs.PostWithoutOwnerDTO;
import lombok.Data;

import java.util.List;

@Data
public class UserWithoutPassDTO {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private int roleId;
    private List<PostWithoutOwnerDTO> posts;
}
