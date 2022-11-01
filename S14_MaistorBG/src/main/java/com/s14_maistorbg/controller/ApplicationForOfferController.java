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

    @PostMapping("/applications")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ApplicationForOfferDTO applicationForOffer(@RequestBody ApplicationForOfferDTO applicationForOfferDTO, HttpServletRequest request) {
        int craftsmanId = getLoggedUserId(request);
        return applicationForOfferService.offerApplication(applicationForOfferDTO, craftsmanId);
    }

    @GetMapping("/applications/{oid}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public List<CraftsmanApplicantDTO> getAllOfferApplicants(@PathVariable int oid, HttpServletRequest request){
        int offerOwnerId = getLoggedUserId(request);
        return applicationForOfferService.getAllOfferApplicants(oid, offerOwnerId);
    }

    @PutMapping("/applications/{oid}/applicant/{cid}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public CraftsmanApplicantDTO selectCraftsmanForOffer(@PathVariable int oid, @PathVariable int cid, HttpServletRequest request){
        int offerOwnerId = getLoggedUserId(request);
        return applicationForOfferService.selectCraftsmanForOffer(oid, cid, offerOwnerId);
    }
}
