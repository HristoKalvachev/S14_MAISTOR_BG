package com.s14_maistorbg.model.repositories;

import com.s14_maistorbg.model.entities.ApplicationForOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationForOfferRepository extends JpaRepository<ApplicationForOffer, Integer> {
}
