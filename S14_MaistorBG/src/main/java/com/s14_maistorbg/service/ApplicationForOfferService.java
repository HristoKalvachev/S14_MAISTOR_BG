package com.s14_maistorbg.service;

import com.s14_maistorbg.model.dto.ApplicationForOfferDTO;
import com.s14_maistorbg.model.entities.ApplicationForOffer;
import com.s14_maistorbg.model.entities.Craftsman;
import com.s14_maistorbg.model.entities.Offer;
import com.s14_maistorbg.model.entities.User;
import com.s14_maistorbg.model.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ApplicationForOfferService extends AbstractService{

    public ApplicationForOfferDTO offerApplication(ApplicationForOfferDTO dto, int id){
        Craftsman craftsman = getCraftsmanById(id);
        Offer offer = offerRepository.findById(dto.getOfferId())
                .orElseThrow(()-> new NotFoundException("Offer does not exist"));
        ApplicationForOffer applicationForOffer = modelMapper.map(dto, ApplicationForOffer.class);
        applicationForOffer.setCraftsman(craftsman);
        applicationForOffer.setSelectedOffer(offer);
        applicationForOffer.setCreatedAt(LocalDateTime.now());
        applicationForOfferRepository.save(applicationForOffer);
        return modelMapper.map(applicationForOffer, ApplicationForOfferDTO.class);
    }
}
