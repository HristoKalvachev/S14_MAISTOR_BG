package com.s14_maistorbg.service;

import com.s14_maistorbg.controller.ExceptionController;
import com.s14_maistorbg.model.dto.offerDTOs.EditOfferDTO;
import com.s14_maistorbg.model.dto.offerDTOs.PostWithoutOwnerDTO;
import com.s14_maistorbg.model.dto.offerDTOs.ResponseOfferDTO;
import com.s14_maistorbg.model.dto.photos.offerPhotos.PhotoOfferWithoutOfferDTO;
import com.s14_maistorbg.model.dto.userDTOs.UserWithoutPostsDTO;
import com.s14_maistorbg.model.entities.Offer;
import com.s14_maistorbg.model.entities.User;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
public class OfferService extends AbstractService{


    public ResponseOfferDTO postOffer(ResponseOfferDTO offerDTO, int ownerId) {
        User user = userRepository.findById(ownerId).orElseThrow(()-> new NotFoundException("User not found!"));
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Offer offer = modelMapper.map(offerDTO, Offer.class);
//        if(user.getRole().getId() == ExceptionController.CRAFTSMAN_ROLE_ID){
//            throw new BadRequestException("");
//        }
        validateOffer(offer);
        offer.setOwner(user);
        offerRepository.save(offer);
        return modelMapper.map(offer, ResponseOfferDTO.class);

    }

    private void validateOffer(Offer offer) {
        if (offer.getOfferTitle().trim().length() < 10) {
            throw new BadRequestException("Write a more describing title!");
        }
        if (offer.getJobDecscription().trim().length() < 10) {
            throw new BadRequestException("Write a better description!");
        }
        if (offer.getBudget() < 0) {
            throw new BadRequestException("Budget must be positive!");
        }
    }

    public ResponseOfferDTO findById(int id) {
        Offer wantedOffer = offerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No such post found!"));
        ResponseOfferDTO dto = modelMapper.map(wantedOffer, ResponseOfferDTO.class);
        dto.setOwner(modelMapper.map(wantedOffer.getOwner(), UserWithoutPostsDTO.class));
        dto.setPhotoOffers(wantedOffer.getOfferPhotos().stream()
                .map(e->modelMapper.map(e, PhotoOfferWithoutOfferDTO.class)).collect(Collectors.toList()));
        return dto;
    }

    public PostWithoutOwnerDTO editOffer(int id, EditOfferDTO editOfferDTO) {
        Offer updatedOffer = offerRepository.findById(id)
                .map(o -> {
                    o.setOfferTitle(editOfferDTO.getOfferTitle());
                    o.setJobDecscription(editOfferDTO.getJobDecscription());
                    o.setBudget(editOfferDTO.getBudget());
                    return offerRepository.save(o);
                }).orElseThrow(() -> new NotFoundException("No such user found!"));
        return modelMapper.map(updatedOffer, PostWithoutOwnerDTO.class);
    }

    public ResponseOfferDTO deleteOffer(int id) {
        Offer wantedOffer = offerRepository.findById(id).orElseThrow(() -> new BadRequestException("No such offer found!"));
        offerRepository.delete(wantedOffer);
        ResponseOfferDTO offerDTO = modelMapper.map(wantedOffer, ResponseOfferDTO.class);
        return offerDTO;
    }
}
