package com.s14_maistorbg.controller;

import com.s14_maistorbg.model.dto.offerDTOs.EditOfferDTO;
import com.s14_maistorbg.model.dto.offerDTOs.PostWithoutOwnerDTO;
import com.s14_maistorbg.model.dto.offerDTOs.ResponseOfferDTO;
import com.s14_maistorbg.model.entities.Offer;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.repositories.OfferRepository;
import com.s14_maistorbg.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OfferController extends ExceptionController {

    @Autowired
    private OfferService offerService;

    @PostMapping("/offers")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseOfferDTO postOffer(@RequestBody ResponseOfferDTO offerDTO) {

        return offerService.postOffer(offerDTO);
    }

    @GetMapping("/offer/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public PostWithoutOwnerDTO findById(@PathVariable int id) {
        return offerService.findById(id);
    }

    @PutMapping("/offers/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public PostWithoutOwnerDTO editOffer(@RequestBody EditOfferDTO editOfferDTO, @PathVariable int id) {

        return offerService.editOffer(id,editOfferDTO);
    }

//    @DeleteMapping("offers/{id}")
//    @ResponseStatus(code = HttpStatus.OK)
//    public Offer deleteOffer(@RequestBody Offer offer, @PathVariable int id) {
//        Offer wantedOffer = offerRepository.findById(id).orElseThrow(() -> new BadRequestException("Can`t delete user!"));
//        offerRepository.delete(wantedOffer);
//        return wantedOffer;
//    }

}
