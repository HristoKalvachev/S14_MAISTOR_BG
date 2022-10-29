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

@Service
public class ApplicationForOfferService extends AbstractService{

    public ApplicationForOfferDTO offerApplication(ApplicationForOfferDTO dto, int id){
        Craftsman craftsman = getCraftsmanById(id);
        Offer offer = findOfferById(id);
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

    public List<CraftsmanApplicantDTO> getAllOfferApplicants(int offerId, int ownerId){
        Offer offer = findOfferById(offerId);
        User offerOwner = getUserById(ownerId);
        if (offer.getOwner() != offerOwner){
            throw new BadRequestException("You are not offer owner!");
        }
        List<CraftsmanApplicantDTO> applicants = new ArrayList<>();
        for (int i = 0; i < offer.getApplicationsForOffer().size(); i++) {
            Craftsman craftsman = offer.getApplicationsForOffer().get(i).getCraftsman();
            User user = getUserById(craftsman.getUserId());
            CraftsmanApplicantDTO applicant = modelMapper.map(user, CraftsmanApplicantDTO.class);
            for (int j = 0; j < craftsman.getMyCategories().size(); j++) {
                applicant.getMyCategories().add(modelMapper.map(craftsman.getMyCategories().get(i), CategoryTypeDTO.class));
            }
        }
        return applicants;
    }

    public CraftsmanApplicantDTO selectCraftsmanForOffer(int offerId, int craftsmanId, int offerOwner){
        Offer offer = findOfferById(offerId);
        User user = getUserById(offerId);
        if (offer.getOwner() != user){
            throw new BadRequestException("You are not offer owner!");
        }
        Craftsman craftsman = null;
        for (int i = 0; i < offer.getApplicationsForOffer().size(); i++) {
            craftsman = offer.getApplicationsForOffer().get(i).getCraftsman();
            if (craftsman.getUserId() == craftsmanId){
                offer.setSelectedCraftsmanId(craftsman);
                offer.setClosed(true);
                break;
            }else {
                throw new NotFoundException("The chosen craftsman did not apply for this offer!");
            }
        }
        return modelMapper.map(craftsman, CraftsmanApplicantDTO.class);
    }

}
