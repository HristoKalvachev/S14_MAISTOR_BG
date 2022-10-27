package com.s14_maistorbg.service;

import com.s14_maistorbg.model.dto.rateDTOs.RateCraftsManDTO;
import com.s14_maistorbg.model.dto.rateDTOs.RateDeleteDTO;
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

    //Todo ASK KRASI HOW TO REFACTOR FINDBYID METHODS
    public RateResponseDTO rateCraftsman(int craftsmanId, RateCraftsManDTO dto) {
        Craftsman craftsMan = craftsManRepository.findById(craftsmanId)
                .orElseThrow(() -> new NotFoundException("Craftsman to be rated not found!"));
        User craftsManToReturn = userRepository.findById(craftsmanId)
                .orElseThrow(() -> new NotFoundException("Craftsman to be rated not found!"));
        User rater = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new NotFoundException("User to rate not found!"));

        int craftsmanRoleID = 2;
        Optional<Rate> checkForAlreadyGivenRate = rateRepository.findByRaterAndCraftsman(rater, craftsMan);
        //Todo extract validations
        if (checkForAlreadyGivenRate.isPresent()) {
            throw new UnauthorizedException("You have already rated this user!");
        }
        if (rater.getId() == craftsMan.getUserId()) {
            throw new UnauthorizedException("Can not rate yourself!");
        }
        if (rater.getRole().getId() == craftsmanRoleID) {
            throw new UnauthorizedException("Craftsmen can`t rate other craftsmen!");
        }
        if (dto.getRating() < 1 || dto.getRating() > 10) {
            throw new BadRequestException("Rate must be between 1 and 10!");
        }

        Rate rate = new Rate();
        rate.setCraftsman(craftsMan);
        rate.setRater(rater);
        rate.setRating(dto.getRating());
        rateRepository.save(rate);
        RateResponseDTO responseDTO = modelMapper.map(rate, RateResponseDTO.class);
        responseDTO.setCraftsman(modelMapper.map(craftsManToReturn, UserWithoutPassDTO.class));
        return responseDTO;
    }

    public RateResponseDTO editRate(int rateId, RateCraftsManDTO dto) {
        Rate rate = rateRepository.findById(rateId).orElseThrow(() -> new NotFoundException("You first need to rate"));
        User rater = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found!"));
        if (rate.getRater().getId() != rater.getId()) {
            throw new UnauthorizedException("Can`t edit other users rates!");
        }
        if (dto.getRating() > 10 || dto.getRating() < 1) {
            throw new BadRequestException("Rate must be between 1 and 10!");
        }
        rate.setRating(dto.getRating());
        rateRepository.save(rate);
        return modelMapper.map(rate, RateResponseDTO.class);
    }

    public String unRate(int rateId, RateDeleteDTO dto) {
        Rate rate = rateRepository.findById(rateId).orElseThrow(() -> new NotFoundException("You first need to rate"));
        User rater = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found!"));
        if (rate.getRater().getId() != rater.getId()) {
            throw new UnauthorizedException("You can`t remove rate!");
        }
        rateRepository.delete(rate);
        return "You have unrated this profile!";
    }
}
