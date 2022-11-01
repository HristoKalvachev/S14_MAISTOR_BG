package com.s14_maistorbg.model.dto.craftsmanDTOs;

import com.s14_maistorbg.model.dto.categoryDTOs.CategoryTypeDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
public class CraftsmanApplicantDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private List<CategoryTypeDTO> myCategories;
}
