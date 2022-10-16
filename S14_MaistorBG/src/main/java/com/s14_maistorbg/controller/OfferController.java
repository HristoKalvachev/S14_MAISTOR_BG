package com.s14_maistorbg.controller;

import com.s14_maistorbg.model.entities.Offer;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OfferController {

    @Autowired
    private OfferRepository offerRepository;

    @PostMapping("/offers")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Offer postOffer(@RequestBody Offer offer) {
        System.out.println(offer.getId());
        System.out.println(offer.getAdType());
        System.out.println(offer.getCityId());
        System.out.println(offer.getOwnerId());
        System.out.println(offer.getRepairCategoryId());
        offerRepository.save(offer);
        return offer;
    }

    @PutMapping("/offers/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseEntity<Offer> editOffer(@RequestBody Offer offer, @PathVariable int id) {
        Offer updatedOffer = offerRepository.findById(id)
                .map(o -> {
                    o.setAdType(offer.getAdType());
                    o.setCityId(offer.getCityId());
                    o.setRepairCategoryId(offer.getRepairCategoryId());
                    return offerRepository.save(o);
                }).orElseThrow(()->new BadRequestException("Bad request! Can`t edit profile!"));
        return new ResponseEntity<>(updatedOffer,HttpStatus.ACCEPTED);
    }

}
