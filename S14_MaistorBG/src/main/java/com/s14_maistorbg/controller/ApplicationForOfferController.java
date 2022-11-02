package com.s14_maistorbg.controller;


import com.s14_maistorbg.model.dto.ApplicationForOfferDTO;
import com.s14_maistorbg.model.dto.craftsmanDTOs.CraftsmanApplicantDTO;
import com.s14_maistorbg.service.ApplicationForOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ApplicationForOfferController extends AbstractController {

    @Autowired
    private ApplicationForOfferService applicationForOfferService;

    @PostMapping("/applications/{oid}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ApplicationForOfferDTO applicationForOffer(@RequestBody ApplicationForOfferDTO applicationForOfferDTO, HttpServletRequest request, @PathVariable int oid) {
        int craftsmanId = getLoggedUserId(request);
        return applicationForOfferService.offerApplication(applicationForOfferDTO, craftsmanId, oid);
    }

    @GetMapping("/applications/{oid}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public List<ApplicationForOfferDTO> getAllOfferApplicants(@PathVariable int oid, HttpServletRequest request){
        int offerOwnerId = getLoggedUserId(request);
        return applicationForOfferService.getAllOfferApplications(oid, offerOwnerId);
    }

    @PutMapping("/applications/{oid}/applicant/{aid}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ApplicationForOfferDTO selectApplicationForOffer(@PathVariable int oid, @PathVariable int aid, HttpServletRequest request){
        int logedUserId = getLoggedUserId(request);
        return applicationForOfferService.selectApplicationForOffer(oid, aid, logedUserId);
    }
}

