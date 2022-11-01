package com.s14_maistorbg.service;

import com.s14_maistorbg.controller.AbstractController;
import com.s14_maistorbg.model.dto.offerDTOs.EditOfferDTO;
import com.s14_maistorbg.model.dto.offerDTOs.OfferWithoutOwnerDTO;
import com.s14_maistorbg.model.dto.offerDTOs.ResponseOfferDTO;
import com.s14_maistorbg.model.dto.photos.offerPhotos.PhotoOfferWithoutOfferDTO;
import com.s14_maistorbg.model.dto.userDTOs.UserWithoutPostsDTO;
import com.s14_maistorbg.model.entities.Craftsman;
import com.s14_maistorbg.model.entities.Offer;
import com.s14_maistorbg.model.entities.User;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.exceptions.NotFoundException;
import com.s14_maistorbg.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OfferService extends AbstractService {

    public ResponseOfferDTO postOffer(ResponseOfferDTO offerDTO, int ownerId) {
        User user = getUserById(ownerId);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Offer offer = modelMapper.map(offerDTO, Offer.class);
        offer.setId(0);
        if(user.getRole().getId() == AbstractController.CRAFTSMAN_ROLE_ID){
            throw new BadRequestException("You can not post offers!");
        }
        offer.setCreatedAt(LocalDateTime.now());
        validateOffer(offer);
        offer.setOwner(user);
        offerRepository.save(offer);
        return modelMapper.map(offer, ResponseOfferDTO.class);
    }

    private void validateOffer(Offer offer) {
        if (offer.getOfferTitle().trim().length() < 10) {
            throw new BadRequestException("Write a more describing title!");
        }
        if (offer.getJobDescription().trim().length() < 10) {
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
                .map(e -> modelMapper.map(e, PhotoOfferWithoutOfferDTO.class)).collect(Collectors.toList()));
        return dto;
    }

    public OfferWithoutOwnerDTO editOffer(int id, EditOfferDTO editOfferDTO) {
        Offer updatedOffer = offerRepository.findById(id)
                .map(o -> {
                    o.setOfferTitle(editOfferDTO.getOfferTitle());
                    o.setJobDescription(editOfferDTO.getJobDescription());
                    o.setBudget(editOfferDTO.getBudget());
                    return offerRepository.save(o);
                }).orElseThrow(() -> new NotFoundException("No such user found!"));
        if(updatedOffer.getOwner().getId()!=editOfferDTO.getOwnerId()){
            throw new UnauthorizedException("Can not edit other offers!");
        }
        return modelMapper.map(updatedOffer, OfferWithoutOwnerDTO.class);
    }

    public ResponseOfferDTO deleteOffer(int id, int userId) {
        Offer wantedOffer = offerRepository.findById(id).orElseThrow(() -> new BadRequestException("No such offer found!"));
        offerRepository.delete(wantedOffer);
        ResponseOfferDTO offerDTO = modelMapper.map(wantedOffer, ResponseOfferDTO.class);
        return offerDTO;
    }

    public List<ResponseOfferDTO> getAllOffersDoneByCraftsman(int craftsmanId) {
        Craftsman craftsman = getCraftsmanById(craftsmanId);
        List<Offer> offersByCraftsman = offerRepository.findBySelectedCraftsmanId(craftsman);
        List<ResponseOfferDTO> offerDTOS = new ArrayList<>();
        for (int i = 0; i < offersByCraftsman.size(); i++) {
            offerDTOS.add(modelMapper.map(offersByCraftsman.get(i), ResponseOfferDTO.class));
        }
        return offerDTOS;
    }

    public List<ResponseOfferDTO> getAll() {
        List<Offer> offers = offerRepository.findAll();
        return offers.stream()
                .map(e -> modelMapper.map(e, ResponseOfferDTO.class)).collect(Collectors.toList());
    }
}
