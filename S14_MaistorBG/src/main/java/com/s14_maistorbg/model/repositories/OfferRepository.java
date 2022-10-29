package com.s14_maistorbg.model.repositories;

import com.s14_maistorbg.model.entities.Craftsman;
import com.s14_maistorbg.model.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {
    @Override
    Optional<Offer> findById(Integer integer);
    List<Offer> findBySelectedCraftsmanId(Craftsman craftsman);
}
