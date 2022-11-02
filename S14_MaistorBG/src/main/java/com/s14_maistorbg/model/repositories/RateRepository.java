package com.s14_maistorbg.model.repositories;

import com.s14_maistorbg.model.entities.Craftsman;
import com.s14_maistorbg.model.entities.Rate;
import com.s14_maistorbg.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer> {

    Optional<Rate> findByRaterAndCraftsman(User user, Craftsman craftsman);

    @Query(value = "SELECT avg(craftsman_ratings.rating) from craftsman_ratings where craftsman_ratings.craftsman_id=?",
            nativeQuery = true)
    double getAvgRateForCraftsman(int id);
}
