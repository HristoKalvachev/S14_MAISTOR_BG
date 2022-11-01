package com.s14_maistorbg.service;

import com.s14_maistorbg.model.dto.categoryDTOs.CategoryDTO;
import com.s14_maistorbg.model.dto.categoryDTOs.CategoryTypeDTO;
import com.s14_maistorbg.model.entities.Category;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.exceptions.NotFoundException;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService extends AbstractService{

    public CategoryDTO addCategory(CategoryTypeDTO categoryTypeDTO){
        validateCategoryType(categoryTypeDTO.getType());
        Category category = new Category();
        category.setType(categoryTypeDTO.getType());
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    public CategoryDTO editCategory(CategoryTypeDTO categoryTypeDTO, int cid) {
        Category category = getCategoryById(cid);
        validateCategoryType(categoryTypeDTO.getType());
        category.setType(categoryTypeDTO.getType());
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    public CategoryDTO deleteCategory(int categoryId){
        Category category = getCategoryById(categoryId);
        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    private void validateCategoryType(String categoryType){
        if (categoryType == null ||
                categoryType.isBlank() ||
                categoryType.isEmpty()){
            throw new BadRequestException("Missing category type!");
        }
        if (categoryType.length() < 3){
            throw new BadRequestException("Add more descriptive category name!");
        }
        if (categoryRepository.existsCategoryByType(categoryType)){
            throw new BadRequestException("The category already exist!");
        }
    }

    public List<CategoryDTO> getAllCategories(){
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        List<Category> categoryList = categoryRepository.findAll();
        for (int i = 0; i < categoryList.size(); i++) {
            categoryDTOList.add(modelMapper.map(categoryList.get(i), CategoryDTO.class));
        }
        return categoryDTOList;
    }
}

