package com.s14_maistorbg.model.repositories;

import com.s14_maistorbg.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findById(int id);

}
