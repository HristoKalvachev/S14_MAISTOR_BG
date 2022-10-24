package com.s14_maistorbg.model.dto.rateDTOs;

import com.s14_maistorbg.model.dto.userDTOs.UserWithoutPassDTO;
import lombok.Data;

@Data
public class RateResponseDTO {

    private int id;
    private UserWithoutPassDTO rater;
    private UserWithoutPassDTO craftsman;
    private int rating;
}
