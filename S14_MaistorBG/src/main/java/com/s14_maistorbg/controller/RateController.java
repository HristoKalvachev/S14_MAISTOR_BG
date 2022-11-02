package com.s14_maistorbg.controller;

import com.s14_maistorbg.model.dto.rateDTOs.RateCraftsManDTO;
import com.s14_maistorbg.model.dto.rateDTOs.RateResponseDTO;
import com.s14_maistorbg.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
public class RateController extends AbstractController {

    @Autowired
    RateService rateService;

    @PostMapping("/craftsman/{cId}/rate")
    @ResponseStatus(value = HttpStatus.OK)
    public RateResponseDTO rateCraftMan(@RequestBody RateCraftsManDTO dto, @PathVariable int cId, HttpServletRequest req) {
        int userId = getLoggedUserId(req);
        return rateService.rateCraftsman(cId, dto, userId);
    }

    @PutMapping("/craftsman/{cId}/rate")
    @ResponseStatus(value = HttpStatus.OK)
    public RateResponseDTO editRate(@RequestBody RateCraftsManDTO dto, @PathVariable int cId, HttpServletRequest req) {
        int userId = getLoggedUserId(req);
        return rateService.editRate(cId, dto, userId);
    }

    @DeleteMapping("/craftsman/{cId}/rate")
    @ResponseStatus(value = HttpStatus.OK)
    public String unRateCraftsman(@PathVariable int cId, HttpServletRequest req) {
        int userId = getLoggedUserId(req);
        return rateService.unRate(cId, userId);
    }

    @GetMapping("/craftsman/{cId}/rate")
    @ResponseStatus(value = HttpStatus.OK)
    public void getRate(@PathVariable int cId){
        rateService.getRate(cId);
    }
}
