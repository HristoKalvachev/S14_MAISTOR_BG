package com.s14_maistorbg.service;

import com.s14_maistorbg.model.dto.categoryDTOs.CategoryTypeDTO;
import com.s14_maistorbg.model.dto.craftsmanDTOs.CraftsmanDTO;
import com.s14_maistorbg.model.entities.Category;
import com.s14_maistorbg.model.entities.Craftsman;
import com.s14_maistorbg.model.entities.User;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CraftsmanService extends AbstractService{

    @Transactional
    public CraftsmanDTO craftsmanAddCategory(int id, String categoryName){
        //Todo refactor everything
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        User userCraftsman = getUserById(id);
        Craftsman craftsman = getCraftsmanById(id);
        Category category = categoryRepository.findByType(categoryName)
                .orElseThrow(()-> new NotFoundException("Category not found!"));
        if (craftsman.getMyCategories().contains(category)){
            throw new BadRequestException("Craftsman already have this category!");
        }
        craftsman.getMyCategories().add(category);
        craftsman = craftsManRepository.save(craftsman);
        category.getMyCraftsmans().add(craftsman);
        categoryRepository.save(category);
        CraftsmanDTO craftsmanDTO = modelMapper.map(userCraftsman, CraftsmanDTO.class);
//        craftsmanDTO.setId(craftsman.getUserId());
//        craftsmanDTO.setFirstName(userCraftsman.getFirstName());
//        craftsmanDTO.setLastName(userCraftsman.getLastName());
//        craftsmanDTO.setUsername(userCraftsman.getUsername());
        for (int i = 0; i < craftsman.getMyCategories().size(); i++) {
            craftsmanDTO.getMyCategories().add(modelMapper.map(craftsman.getMyCategories().get(i), CategoryTypeDTO.class));
        }
        return craftsmanDTO;
    }

    @Transactional
    public CraftsmanDTO craftsmanDeleteCategory(int id, String categoryName){
        //Todo
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        User userCraftsman = getUserById(id);
        Craftsman craftsman = getCraftsmanById(id);
        Category category = categoryRepository.findByType(categoryName)
                .orElseThrow(()-> new NotFoundException("Category not found!"));
        if (!craftsman.getMyCategories().contains(category)){
            throw new BadRequestException("Craftsman does not have this category!");
        }
        craftsman.getMyCategories().remove(category);
        craftsman = craftsManRepository.save(craftsman);
        category.getMyCraftsmans().remove(craftsman);
        categoryRepository.save(category);
        CraftsmanDTO craftsmanDTO = modelMapper.map(userCraftsman, CraftsmanDTO.class);
//        craftsmanDTO.setId(craftsman.getUserId());
//        craftsmanDTO.setFirstName(userCraftsman.getFirstName());
//        craftsmanDTO.setLastName(userCraftsman.getLastName());
//        craftsmanDTO.setUsername(userCraftsman.getUsername());
        for (int i = 0; i < craftsman.getMyCategories().size(); i++) {
            craftsmanDTO.getMyCategories().add(modelMapper.map(craftsman.getMyCategories().get(i), CategoryTypeDTO.class));
        }
        return craftsmanDTO;
    }

}
