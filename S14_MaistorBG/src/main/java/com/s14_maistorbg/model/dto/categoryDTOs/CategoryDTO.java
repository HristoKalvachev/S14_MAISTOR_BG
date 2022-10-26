package com.s14_maistorbg.model.dto.categoryDTOs;

import lombok.*;

import javax.persistence.Column;

@Setter
@Getter
@NoArgsConstructor
public class CategoryDTO {

    private int id;
    private String type;

}
