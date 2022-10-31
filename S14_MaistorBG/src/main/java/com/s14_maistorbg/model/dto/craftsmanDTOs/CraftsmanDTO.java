package com.s14_maistorbg.model.dto.craftsmanDTOs;

import com.s14_maistorbg.model.dto.categoryDTOs.CategoryTypeDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CraftsmanDTO {
    private int userId;
    private String description;
    List<CategoryTypeDTO> categories = new ArrayList<>();
}
