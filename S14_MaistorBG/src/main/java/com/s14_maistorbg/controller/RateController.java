package com.s14_maistorbg.controller;

import com.s14_maistorbg.model.dto.rateDTOs.RateCraftsManDTO;
import com.s14_maistorbg.model.dto.rateDTOs.RateDeleteDTO;
import com.s14_maistorbg.model.dto.rateDTOs.RateResponseDTO;
import com.s14_maistorbg.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class RateController extends ExceptionController {

    @Autowired
    RateService rateService;

    @PostMapping("/craftsman/{cId}/rate")
    public RateResponseDTO rateCraftMan(@RequestBody RateCraftsManDTO dto, @PathVariable int cId) {
        return rateService.rateCraftsman(cId, dto);
    }

    @PutMapping("/rate/{rateId}")
    public RateResponseDTO editRate(@RequestBody RateCraftsManDTO dto, @PathVariable int rateId) {
        return rateService.editRate(rateId, dto);
    }

    @DeleteMapping("/rate/{rateId}")
    public String unRateCraftsman(@RequestBody RateDeleteDTO dto, @PathVariable int rateId) {
        return rateService.unRate(rateId, dto);
    }
}
