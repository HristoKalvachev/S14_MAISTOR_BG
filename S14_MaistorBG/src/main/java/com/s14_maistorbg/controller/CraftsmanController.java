package com.s14_maistorbg.controller;

import com.s14_maistorbg.model.dto.categoryDTOs.CategoryTypeDTO;
import com.s14_maistorbg.model.dto.craftsmanDTOs.CraftsmanDTO;
import com.s14_maistorbg.model.dto.craftsmanDTOs.CraftsmanDescriptionDTO;
import com.s14_maistorbg.model.entities.Craftsman;
import com.s14_maistorbg.service.CraftsmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CraftsmanController extends ExceptionController{

    @Autowired
    private CraftsmanService craftsmanService;

    @PostMapping("/craftsman_categories")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CraftsmanDTO craftsmanAddCategory(@RequestBody CategoryTypeDTO categoryTypeDTO, HttpServletRequest request){
        int id = getLoggedUserId(request);
        return craftsmanService.craftsmanAddCategory(id, categoryTypeDTO.getType());
    }

    @DeleteMapping("/craftsman_categories")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public CraftsmanDTO craftsmanDeleteCategory(@RequestBody CategoryTypeDTO categoryTypeDTO, HttpServletRequest request){
        int id = getLoggedUserId(request);
        return craftsmanService.craftsmanDeleteCategory(id, categoryTypeDTO.getType());
    }

    @PutMapping("/craftsman/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public CraftsmanDTO  writeDescription(@RequestBody CraftsmanDescriptionDTO dto,@PathVariable int id){
        return craftsmanService.writeDescription(dto,id);
    }


}
