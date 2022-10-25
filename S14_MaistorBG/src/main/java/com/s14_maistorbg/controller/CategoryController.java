package com.s14_maistorbg.controller;

import com.s14_maistorbg.model.dto.categoryDTOs.CategoryDTO;
import com.s14_maistorbg.model.dto.categoryDTOs.CategoryTypeDTO;
import com.s14_maistorbg.model.dto.commentsDTOs.AddCommentDTO;
import com.s14_maistorbg.model.dto.commentsDTOs.ResponseCommentDTO;
import com.s14_maistorbg.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CategoryController extends ExceptionController{

    @Autowired
    CategoryService categoryService;

    @PostMapping("/repair_categories")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CategoryDTO addCategory(@RequestBody CategoryTypeDTO categoryTypeDTO, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        return categoryService.addCategory(categoryTypeDTO);
    }
}
