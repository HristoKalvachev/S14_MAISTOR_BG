package com.s14_maistorbg.model.repositories;

import com.s14_maistorbg.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findById(int id);
    boolean existsCategoryByType(String categoryName);
    @Override
    List<Category> findAll();
    Optional<Category> findByType(String categoryName);

}
