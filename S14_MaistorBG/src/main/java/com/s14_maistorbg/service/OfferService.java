package com.s14_maistorbg.service;

import com.s14_maistorbg.model.dto.offerDTOs.EditOfferDTO;
import com.s14_maistorbg.model.dto.offerDTOs.PostWithoutOwnerDTO;
import com.s14_maistorbg.model.dto.offerDTOs.ResponseOfferDTO;
import com.s14_maistorbg.model.entities.Offer;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.exceptions.NotFoundException;
import com.s14_maistorbg.model.repositories.OfferRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ResponseOfferDTO postOffer(ResponseOfferDTO offerDTO) {
        Offer offer = modelMapper.map(offerDTO, Offer.class);
        if (offer.getOfferTitle().trim().length() < 5) {
            throw new BadRequestException("Write a more describing title!");
        }
        if (offer.getJobDescription().trim().length() < 10) {
            throw new BadRequestException("Write a better description!");
        }
        if (offer.getBudget() < 0) {
            throw new BadRequestException("Budget must be positive!");
        }
        offerRepository.save(offer);
        return modelMapper.map(offer, ResponseOfferDTO.class);

    }

    public PostWithoutOwnerDTO findById(int id) {
        Offer wantedOffer = offerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No such post found!"));
        return modelMapper.map(wantedOffer, PostWithoutOwnerDTO.class);
    }

    public PostWithoutOwnerDTO editOffer(int id, EditOfferDTO editOfferDTO) {
        Offer updatedOffer = offerRepository.findById(id)
                .map(o -> {
                    o.setOfferTitle(editOfferDTO.getOfferTitle());
                    o.setJobDescription(editOfferDTO.getJobDescription());
                    o.setBudget(editOfferDTO.getBudget());
                    o.setCityId(editOfferDTO.getCityId());
                    o.setRepairCategoryId(editOfferDTO.getRepairCategoryId());
                    return offerRepository.save(o);
                }).orElseThrow(() -> new NotFoundException("No such user found!"));
        return modelMapper.map(updatedOffer,PostWithoutOwnerDTO.class);
    }
}
