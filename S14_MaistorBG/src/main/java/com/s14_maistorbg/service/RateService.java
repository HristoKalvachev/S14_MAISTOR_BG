package com.s14_maistorbg.service;

import com.s14_maistorbg.model.dto.rateDTOs.RateCraftsManDTO;
import com.s14_maistorbg.model.dto.rateDTOs.RateResponseDTO;
import com.s14_maistorbg.model.entities.Craftsman;
import com.s14_maistorbg.model.entities.Rate;
import com.s14_maistorbg.model.entities.User;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.exceptions.NotFoundException;
import com.s14_maistorbg.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

@Service
public class RateService extends AbstractService {

    public RateResponseDTO rateCraftsman(int craftsmanId, RateCraftsManDTO dto) {
        User craftsMan = userRepository.findById(craftsmanId)
                .orElseThrow(() -> new NotFoundException("Craftsman to be rated not found!"));
        User rater = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new NotFoundException("User to rate not found!"));

        int craftsmanRoleID = 2;
        if (rater.getId() == craftsMan.getId()) {
            throw new UnauthorizedException("Can not rate yourself!");
        }
        if (rater.getRole().getId() == craftsmanRoleID) {
            throw new UnauthorizedException("Craftsmen can`t rate other craftsmen!");
        }
        if (dto.getRating() < 1 || dto.getRating() > 10) {
            throw new BadRequestException("Rate must be between 1 and 10!");
        }
        //TODO - make a check if craftsman has been already rated by this user

        Craftsman craftsman = craftsManRepository.findById(craftsMan.getId())
                .orElseThrow(() -> new NotFoundException("Craftsman not found!"));
        Rate rate = new Rate();
        rate.setCraftsman(craftsman);
        rate.setRater(rater);
        rate.setRating(dto.getRating());
        rateRepository.save(rate);

        return modelMapper.map(rate, RateResponseDTO.class);
    }

    public RateResponseDTO editRate(int rateId, RateCraftsManDTO dto) {
        Rate rate = rateRepository.findById(rateId).orElseThrow(() -> new NotFoundException("You first need to rate"));
        User rater = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found!"));
        if(rate.getRater().getId()!=rater.getId()){
            throw new UnauthorizedException("Can`t edit other users rates!");
        }
        if(dto.getRating()>10 || dto.getRating()<1){
            throw new BadRequestException("Rate must be between 1 and 10!");
        }
        rate.setRating(dto.getRating());
        rateRepository.save(rate);
        return modelMapper.map(rate,RateResponseDTO.class);
    }
}
