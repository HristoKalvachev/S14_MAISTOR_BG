package com.s14_maistorbg.model.dto.rateDTOs;

import com.s14_maistorbg.model.dto.userDTOs.UserWithoutPassDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class RateResponseDTO {

    private int id;
    private UserWithoutPassDTO rater;
    private UserWithoutPassDTO craftsman;
    private int rating;
}
