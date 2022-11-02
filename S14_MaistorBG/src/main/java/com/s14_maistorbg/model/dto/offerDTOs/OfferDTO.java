package com.s14_maistorbg.model.dto.offerDTOs;

import com.s14_maistorbg.model.dto.photos.offerPhotos.PhotoOfferWithoutOfferDTO;
import com.s14_maistorbg.model.dto.userDTOs.UserWithoutPostsDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OfferDTO {
    private String offerTitle;
    private String jobDescription;
    private double budget;
    private int cityId;
    private int repairCategoryId;
    private LocalDateTime durationData;
    private UserWithoutPostsDTO owner;
    private List<PhotoOfferWithoutOfferDTO> photoOffers;
    private boolean isClosed;
    private LocalDateTime createdAt;
}
