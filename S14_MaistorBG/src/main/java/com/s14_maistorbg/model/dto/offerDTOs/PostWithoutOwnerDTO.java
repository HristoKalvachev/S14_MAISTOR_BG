package com.s14_maistorbg.model.dto.offerDTOs;

import lombok.Data;

@Data
public class PostWithoutOwnerDTO {

    private int id;
    private String offerTitle;
    private String jobDecscription;
    private double budget;
    private int cityId;
    private int repairCategoryId;
}
