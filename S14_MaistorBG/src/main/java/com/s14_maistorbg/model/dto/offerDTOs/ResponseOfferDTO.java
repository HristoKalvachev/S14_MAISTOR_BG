package com.s14_maistorbg.model.dto.offerDTOs;

import lombok.Data;

@Data
public class ResponseOfferDTO {

    private String offerTitle;
    private String jobDescription;
    private double budget;
    private int cityId;
    private int repairCategoryId;
    private int ownerId;
}
