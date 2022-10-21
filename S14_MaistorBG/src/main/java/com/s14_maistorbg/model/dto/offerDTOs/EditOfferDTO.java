package com.s14_maistorbg.model.dto.offerDTOs;

import lombok.Data;

@Data
public class EditOfferDTO {

    private String offerTitle;
    private String jobDecscription;
    private double budget;
    private int cityId;
    private int repairCategoryId;

}
