package com.s14_maistorbg.model.dto.userDTOs;

import com.s14_maistorbg.model.dto.offerDTOs.OfferWithoutOwnerDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserWithoutPassDTO {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private int roleId;
    private List<OfferWithoutOwnerDTO> posts;
}
