package com.s14_maistorbg.service;

import com.s14_maistorbg.model.dto.categoryDTOs.CategoryTypeDTO;
import com.s14_maistorbg.model.dto.commentsDTOs.CommentWithUsernameDTO;
import com.s14_maistorbg.model.dto.craftsmanDTOs.CraftsmanDTO;
import com.s14_maistorbg.model.dto.craftsmanDTOs.CraftsmanDescriptionDTO;
import com.s14_maistorbg.model.dto.craftsmanDTOs.CraftsmanProfileDTO;
import com.s14_maistorbg.model.entities.Category;
import com.s14_maistorbg.model.entities.Craftsman;
import com.s14_maistorbg.model.entities.User;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CraftsmanService extends AbstractService {

    @Transactional
    public CraftsmanDTO craftsmanAddCategory(int id, String categoryName) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        User userCraftsman = getUserById(id);
        Craftsman craftsman = getCraftsmanById(id);
        Category category = getCategoryByType(categoryName);
        if (craftsman.getMyCategories().contains(category)) {
            throw new BadRequestException("Craftsman already have this category!");
        }
        craftsman.getMyCategories().add(category);
        craftsman = craftsManRepository.save(craftsman);
        category.getMyCraftsmans().add(craftsman);
        categoryRepository.save(category);
        CraftsmanDTO craftsmanDTO = modelMapper.map(userCraftsman, CraftsmanDTO.class);
        for (int i = 0; i < craftsman.getMyCategories().size(); i++) {
            craftsmanDTO.getCategories().add(modelMapper.map(craftsman.getMyCategories().get(i), CategoryTypeDTO.class));
        }
        return craftsmanDTO;
    }

    @Transactional
    public CraftsmanDTO craftsmanDeleteCategory(int id, String categoryName) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        User userCraftsman = getUserById(id);
        Craftsman craftsman = getCraftsmanById(id);
        Category category = categoryRepository.findByType(categoryName)
                .orElseThrow(() -> new NotFoundException("Category not found!"));
        if (!craftsman.getMyCategories().contains(category)) {
            throw new BadRequestException("Craftsman does not have this category!");
        }
        craftsman.getMyCategories().remove(category);
        craftsman = craftsManRepository.save(craftsman);
        category.getMyCraftsmans().remove(craftsman);
        categoryRepository.save(category);
        CraftsmanDTO craftsmanDTO = modelMapper.map(userCraftsman, CraftsmanDTO.class);
        for (int i = 0; i < craftsman.getMyCategories().size(); i++) {
            craftsmanDTO.getCategories().add(modelMapper.map(craftsman.getMyCategories().get(i), CategoryTypeDTO.class));
        }
        return craftsmanDTO;
    }


    public CraftsmanDTO writeDescription(CraftsmanDescriptionDTO dto, int id) {
        Craftsman craftsman = getCraftsmanById(id);
        int minLength = 10;
        if (dto.getDescription().length() < minLength) {
            throw new BadRequestException("Write a better description about yourself!");
        }
        craftsman.setDescription(dto.getDescription());
        CraftsmanDTO craftsmanDTO = modelMapper.map(craftsman, CraftsmanDTO.class);
        craftsManRepository.save(craftsman);
        return craftsmanDTO;
    }

    public CraftsmanProfileDTO showProfile(int id) {
        Craftsman craftsman = getCraftsmanById(id);
        User user = getUserById(id);
        CraftsmanProfileDTO craftsmanProfileDTO;
        craftsmanProfileDTO = setProfileDto(craftsman, user);
        return craftsmanProfileDTO;
    }

    private CraftsmanProfileDTO setProfileDto(Craftsman craftsman, User user) {
        CraftsmanProfileDTO craftsmanProfileDTO = modelMapper.map(user, CraftsmanProfileDTO.class);
        craftsmanProfileDTO.setDescription(craftsman.getDescription());
        List<CategoryTypeDTO> categoryTypeDTOS = craftsman.getMyCategories().stream()
                .map(e -> modelMapper.map(e, CategoryTypeDTO.class)).collect(Collectors.toList());
        craftsmanProfileDTO.setCategories(categoryTypeDTOS);

        List<CommentWithUsernameDTO> commentWithUsernameDTOS = craftsman.getMyAccountComments().stream()
                .map(e -> modelMapper.map(e, CommentWithUsernameDTO.class)).collect(Collectors.toList());
        craftsmanProfileDTO.setComments(commentWithUsernameDTOS);
        craftsmanProfileDTO.setPhotos(craftsman.getMyPhotos());
        double rate = rateRepository.getAvgRateForCraftsman(craftsman.getUserId()).orElse(0.0);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        rate = Double.parseDouble(decimalFormat.format(rate));
        craftsmanProfileDTO.setRating(rate);
        return craftsmanProfileDTO;
    }

    public List<CraftsmanDTO> getAll() {
        List<Craftsman> craftsmen = craftsManRepository.findAll();
        return craftsmen.stream().map(e -> modelMapper.map(e, CraftsmanDTO.class)).collect(Collectors.toList());
    }

    public List<CraftsmanDTO> getAllByCategory(int id) {
        Category category = getCategoryById(id);
        List<Craftsman> craftsmen = craftsManRepository.findAllByMyCategoriesContaining(category);

        return craftsmen.stream().map(e->modelMapper.map(e,CraftsmanDTO.class)).collect(Collectors.toList());
    }
}
