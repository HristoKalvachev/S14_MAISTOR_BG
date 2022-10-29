package com.s14_maistorbg.model.dto.craftsmanDTOs;

import com.s14_maistorbg.model.dto.categoryDTOs.CategoryDTO;
import com.s14_maistorbg.model.dto.categoryDTOs.CategoryTypeDTO;
import com.s14_maistorbg.model.entities.Category;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CraftsmanDTO {
    private int id;
    private String description;
    List<CategoryTypeDTO> myCategories = new ArrayList<>();
}
