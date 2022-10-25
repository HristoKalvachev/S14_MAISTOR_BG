package com.s14_maistorbg.model.dto.offerDTOs;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostWithoutOwnerDTO {

    private int id;
    private String offerTitle;
    private String jobDecscription;
    private double budget;
    private int cityId;
    private int repairCategoryId;
}
