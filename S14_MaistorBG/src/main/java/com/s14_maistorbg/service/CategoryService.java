package com.s14_maistorbg.service;

import com.s14_maistorbg.model.dto.categoryDTOs.CategoryDTO;
import com.s14_maistorbg.model.dto.categoryDTOs.CategoryTypeDTO;
import com.s14_maistorbg.model.entities.Category;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractService{
    public CategoryDTO addCategory(CategoryTypeDTO categoryTypeDTO){
        if (categoryTypeDTO.getType() == null ||
                categoryTypeDTO.getType().isBlank() ||
                categoryTypeDTO.getType().isEmpty()){
            throw new BadRequestException("Missing category type!");
        }
        if (categoryTypeDTO.getType().length() < 3){
            throw new BadRequestException("Add more descriptive category name!");
        }
        if (categoryRepository.existsCategoryByType(categoryTypeDTO.getType())){
            throw new BadRequestException("The category already exist!");
        }
        Category category = new Category();
        category.setType(categoryTypeDTO.getType());
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryDTO.class);
    }


}

