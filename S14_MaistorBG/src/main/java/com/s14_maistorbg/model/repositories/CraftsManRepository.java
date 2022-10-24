package com.s14_maistorbg.model.repositories;

import com.s14_maistorbg.model.entities.Category;
import com.s14_maistorbg.model.entities.Craftsman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CraftsManRepository extends JpaRepository<Craftsman,Integer> {

    Optional<Craftsman> findCraftsmanByCategory(Category category);
    Optional<Craftsman> findByUserId(int id);

}
