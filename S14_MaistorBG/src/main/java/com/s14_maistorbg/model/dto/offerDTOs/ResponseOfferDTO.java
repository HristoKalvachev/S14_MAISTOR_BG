package com.s14_maistorbg.model.dto.offerDTOs;

import com.s14_maistorbg.model.dto.userDTOs.UserWithoutPostsDTO;
import lombok.Data;

@Data
public class ResponseOfferDTO {

    private String offerTitle;
    private String jobDecscription;
    private double budget;
    private int cityId;
    private int repairCategoryId;
    private UserWithoutPostsDTO owner;
}
