package com.s14_maistorbg.service;

import com.s14_maistorbg.model.dto.ApplicationForOfferDTO;
import com.s14_maistorbg.model.dto.categoryDTOs.CategoryTypeDTO;
import com.s14_maistorbg.model.dto.craftsmanDTOs.CraftsmanApplicantDTO;
import com.s14_maistorbg.model.entities.ApplicationForOffer;
import com.s14_maistorbg.model.entities.Craftsman;
import com.s14_maistorbg.model.entities.Offer;
import com.s14_maistorbg.model.entities.User;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationForOfferService extends AbstractService{

    public ApplicationForOfferDTO offerApplication(ApplicationForOfferDTO dto, int craftsmanId, int offerId){
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Craftsman craftsman = getCraftsmanById(craftsmanId);
        Offer offer = getOfferById(offerId);
        if (offer.isClosed()){
            throw new BadRequestException("The offer is closed!");
        }
        ApplicationForOffer applicationForOffer = modelMapper.map(dto, ApplicationForOffer.class);
        applicationForOffer.setCraftsman(craftsman);
        applicationForOffer.setAppliedOffer(offer);
        applicationForOffer.setCreatedAt(LocalDateTime.now());
        applicationForOfferRepository.save(applicationForOffer);
        return modelMapper.map(applicationForOffer, ApplicationForOfferDTO.class);
    }

    public List<ApplicationForOfferDTO> getAllOfferApplications(int offerId, int ownerId){
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Offer offer = getOfferById(offerId);
        User offerOwner = getUserById(ownerId);
        if (offer.getOwner() != offerOwner){
            throw new BadRequestException("You are not offer owner!");
        }
        List<ApplicationForOfferDTO> applications = new ArrayList<>();
        for (int i = 0; i < offer.getApplicationsForOffer().size(); i++) {
            applications.add(modelMapper.map(offer.getApplicationsForOffer().get(i), ApplicationForOfferDTO.class));
        }
        return applications;
    }

    public ApplicationForOfferDTO selectApplicationForOffer(int offerId, int applicationId, int offerOwner){
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Offer offer = getOfferById(offerId);
        if (offer.getOwner().getId() != offerOwner){
            throw new BadRequestException("You are not offer owner!");
        }
        if (offer.isClosed()){
            throw new BadRequestException("The offer is closed!");
        }
        ApplicationForOffer application = applicationForOfferRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException("Application not found!"));
        if(application.getAppliedOffer().getId() == offerId){
            offer.setSelectedApplication(application);
            offer.setClosed(true);
            offerRepository.save(offer);
        }
        return modelMapper.map(application, ApplicationForOfferDTO.class);
    }

}
