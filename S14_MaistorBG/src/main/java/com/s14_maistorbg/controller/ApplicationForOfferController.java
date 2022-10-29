package com.s14_maistorbg.controller;


import com.s14_maistorbg.model.dto.ApplicationForOfferDTO;
import com.s14_maistorbg.model.dto.categoryDTOs.CategoryDTO;
import com.s14_maistorbg.model.dto.categoryDTOs.CategoryTypeDTO;
import com.s14_maistorbg.service.ApplicationForOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ApplicationForOfferController extends ExceptionController{

    @Autowired
    private ApplicationForOfferService applicationForOfferService;

    @PostMapping("/applications")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ApplicationForOfferDTO applicationForOffer(@RequestBody ApplicationForOfferDTO applicationForOfferDTO, HttpServletRequest request) {
        int craftsmanId = getLoggedUserId(request);
        return applicationForOfferService.offerApplication(applicationForOfferDTO, craftsmanId);
    }

//    @GetMapping("/applications")
//    @ResponseStatus(code = HttpStatus.ACCEPTED)
//    public List<ApplicationForOfferDTO> getAllOfferApplicants(){
//
//    }

}
