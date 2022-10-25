package com.s14_maistorbg.model.repositories;

import com.s14_maistorbg.model.entities.Craftsman;
import com.s14_maistorbg.model.entities.Rate;
import com.s14_maistorbg.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RateRepository extends JpaRepository<Rate, Integer> {

    Optional<Rate> findByRaterAndCraftsman(User user, Craftsman craftsman);
}
