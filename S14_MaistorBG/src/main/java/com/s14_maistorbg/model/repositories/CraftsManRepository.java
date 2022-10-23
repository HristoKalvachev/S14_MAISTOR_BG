package com.s14_maistorbg.model.repositories;

import com.s14_maistorbg.model.entities.Craftsman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CraftsManRepository extends JpaRepository<Craftsman,Integer> {


}
