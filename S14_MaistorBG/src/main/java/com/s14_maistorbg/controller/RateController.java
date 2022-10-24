package com.s14_maistorbg.controller;

import com.s14_maistorbg.model.dto.rateDTOs.RateCraftsManDTO;
import com.s14_maistorbg.model.dto.rateDTOs.RateResponseDTO;
import com.s14_maistorbg.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class RateController extends ExceptionController {

    @Autowired
    RateService rateService;

    @PostMapping("/rate/{id}")
    public RateResponseDTO rateCraftMan(@RequestBody RateCraftsManDTO dto, @PathVariable int craftsManId) {

        return rateService.rateCraftsman(craftsManId, dto);
    }

    @PutMapping("/rate/{rateId}")
    public RateResponseDTO editRate(@RequestBody RateCraftsManDTO dto, @PathVariable int rateId) {
        return rateService.editRate(rateId, dto);
    }
}
