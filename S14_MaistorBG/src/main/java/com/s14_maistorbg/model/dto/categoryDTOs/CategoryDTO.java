package com.s14_maistorbg.model.dto.categoryDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
@NoArgsConstructor
public class CategoryDTO {

    private int id;
    private String type;

}
