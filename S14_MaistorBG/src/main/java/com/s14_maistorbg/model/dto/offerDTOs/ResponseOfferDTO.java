package com.s14_maistorbg.model.dto.offerDTOs;

import com.s14_maistorbg.model.dto.photos.offerPhotos.PhotoOfferWithoutOfferDTO;
import com.s14_maistorbg.model.dto.userDTOs.UserWithoutPostsDTO;
import com.s14_maistorbg.model.entities.Comment;
import com.s14_maistorbg.model.entities.PhotoOffer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ResponseOfferDTO {

    private String offerTitle;
    private String jobDescription;
    private double budget;
    private int cityId;
    private int repairCategoryId;
    private LocalDate durationData;
    private UserWithoutPostsDTO owner;
    private List<PhotoOfferWithoutOfferDTO> photoOffers;
    private boolean isClosed;
    private LocalDateTime createdAt;


}
