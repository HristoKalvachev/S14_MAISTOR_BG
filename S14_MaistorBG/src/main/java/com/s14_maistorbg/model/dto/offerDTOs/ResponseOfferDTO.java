package com.s14_maistorbg.model.dto.offerDTOs;

import com.s14_maistorbg.model.dto.photos.offerPhotos.PhotoOfferWithoutOfferDTO;
import com.s14_maistorbg.model.dto.userDTOs.UserWithoutPostsDTO;
import com.s14_maistorbg.model.entities.PhotoOffer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class ResponseOfferDTO {

    private String offerTitle;
    private String jobDescription;
    private double budget;
    private int cityId;
    private int repairCategoryId;
    private UserWithoutPostsDTO owner;
    private List<PhotoOfferWithoutOfferDTO> photoOffers;
}
