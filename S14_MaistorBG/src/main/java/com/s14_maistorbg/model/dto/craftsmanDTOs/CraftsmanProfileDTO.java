package com.s14_maistorbg.model.dto.craftsmanDTOs;

import com.s14_maistorbg.model.dto.categoryDTOs.CategoryTypeDTO;
import com.s14_maistorbg.model.dto.dtosWithoutOwners.CityWithoutOwnerDTO;
import com.s14_maistorbg.model.dto.commentsDTOs.CommentWithUsernameDTO;
import com.s14_maistorbg.model.dto.dtosWithoutOwners.RoleWithoutOwnerDTO;
import com.s14_maistorbg.model.dto.offerDTOs.OfferWithoutOwnerDTO;
import com.s14_maistorbg.model.entities.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CraftsmanProfileDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String description;
    private String email;
    private CityWithoutOwnerDTO city;
    private LocalDate registeredAt;
    private String profilePicUrl;
    private RoleWithoutOwnerDTO role;
    private List<CommentWithUsernameDTO> comments;
    private List<CategoryTypeDTO> categories;
    private List<PhotoCraftsman> photos;
}