package com.s14_maistorbg.model.dto.offerDTOs;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditOfferDTO {

    private String offerTitle;
    private String jobDescription;
    private int ownerId;
    private double budget;
    private int cityId;
    private int repairCategoryId;

}
