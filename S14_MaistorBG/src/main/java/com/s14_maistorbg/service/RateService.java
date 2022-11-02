package com.s14_maistorbg.service;

import com.s14_maistorbg.controller.AbstractController;
import com.s14_maistorbg.model.dto.rateDTOs.RateCraftsManDTO;
import com.s14_maistorbg.model.dto.rateDTOs.RateResponseDTO;
import com.s14_maistorbg.model.dto.userDTOs.UserWithoutPassDTO;
import com.s14_maistorbg.model.entities.Craftsman;
import com.s14_maistorbg.model.entities.Rate;
import com.s14_maistorbg.model.entities.User;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.exceptions.NotFoundException;
import com.s14_maistorbg.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RateService extends AbstractService {

    private final static int MIN_RATE = 1;
    private final static int MAX_RATE = 10;

    public RateResponseDTO rateCraftsman(int craftsmanId, RateCraftsManDTO dto, int userId) {
        Craftsman craftsMan = getCraftsmanById(craftsmanId);

        User craftsmanAsUser = userRepository.findById(craftsmanId)
                .orElseThrow(() -> new NotFoundException("Craftsman to be rated not found!"));
        User rater = getUserById(userId);


        Optional<Rate> checkForAlreadyGivenRate = rateRepository.findByRaterAndCraftsman(rater, craftsMan);
        int craftsmanRoleID = AbstractController.CRAFTSMAN_ROLE_ID;
        rateValidation(dto, craftsMan, rater, craftsmanRoleID, checkForAlreadyGivenRate);

        Rate rate = new Rate();
        rate.setCraftsman(craftsMan);
        rate.setRater(rater);
        rate.setRating(dto.getRating());
        rateRepository.save(rate);
        RateResponseDTO responseDTO = modelMapper.map(rate, RateResponseDTO.class);
        responseDTO.setCraftsman(modelMapper.map(craftsmanAsUser, UserWithoutPassDTO.class));
        return responseDTO;
    }

    private void rateValidation(RateCraftsManDTO dto, Craftsman craftsMan, User rater, int craftsmanRoleID, Optional<Rate> checkForAlreadyGivenRate) {
        if (checkForAlreadyGivenRate.isPresent()) {
            throw new UnauthorizedException("You have already rated this user!");
        }
        if (rater.getId() == craftsMan.getUserId()) {
            throw new UnauthorizedException("Can not rate yourself!");
        }
        if (rater.getRole().getId() == craftsmanRoleID) {
            throw new UnauthorizedException("Craftsmen can`t rate other craftsmen!");
        }
        if (dto.getRating() < MIN_RATE || dto.getRating() > MAX_RATE) {
            throw new BadRequestException("Rate must be between 1 and 10!");
        }
    }

    public RateResponseDTO editRate(int cid, RateCraftsManDTO dto, int userId) {
        User rater = getUserById(userId);
        Craftsman craftsman = getCraftsmanById(cid);
        Rate rate = rateRepository.findByRaterAndCraftsman(rater, craftsman)
                .orElseThrow(() -> new UnauthorizedException("You first need to rate!"));
        if (rate.getRater().getId() != rater.getId()) {
            throw new UnauthorizedException("Can`t edit other users rates!");
        }
        if (dto.getRating() > MAX_RATE || dto.getRating() < MIN_RATE) {
            throw new BadRequestException("Rate must be between " + MIN_RATE + " and " + MAX_RATE + "!");
        }
        rate.setRating(dto.getRating());
        rateRepository.save(rate);
        return modelMapper.map(rate, RateResponseDTO.class);
    }

    public String unRate(int cId, int userId) {
        User rater = getUserById(userId);
        Craftsman craftsman = getCraftsmanById(cId);
        Rate rate = rateRepository.findByRaterAndCraftsman(rater, craftsman)
                .orElseThrow(() -> new UnauthorizedException("You first need to rate!"));
        if (rate.getRater().getId() != rater.getId()) {
            throw new UnauthorizedException("You can`t remove rate!");
        }
        rateRepository.delete(rate);
        return "You have unrated this profile!";
    }

    public void getRate(int cid){
        Craftsman craftsman =getCraftsmanById(cid);
        double rate =rateRepository.getAvgRateForCraftsman(4);
        rate = Math.round(rate);
    }
}
