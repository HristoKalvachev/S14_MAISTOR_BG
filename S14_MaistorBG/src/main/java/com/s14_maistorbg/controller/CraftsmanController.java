package com.s14_maistorbg.controller;

import com.s14_maistorbg.model.dto.categoryDTOs.CategoryTypeDTO;
import com.s14_maistorbg.model.dto.craftsmanDTOs.CraftsmanDTO;
import com.s14_maistorbg.service.CraftsmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CraftsmanController extends ExceptionController{

    @Autowired
    private CraftsmanService craftsmanService;

    @PostMapping("/craftsman_categories/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CraftsmanDTO addCraftsmanCategory(@RequestBody CategoryTypeDTO categoryTypeDTO, HttpServletRequest request){
        int id = getLoggedUserId(request);
        System.out.println(categoryTypeDTO.getType());
        return craftsmanService.craftsmanAddCategory(id, categoryTypeDTO.getType());
    }


}
