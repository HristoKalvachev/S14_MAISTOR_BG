package com.s14_maistorbg.controller;

import com.s14_maistorbg.model.dto.categoryDTOs.CategoryDTO;
import com.s14_maistorbg.model.dto.categoryDTOs.CategoryTypeDTO;
import com.s14_maistorbg.model.dto.commentsDTOs.AddCommentDTO;
import com.s14_maistorbg.model.dto.commentsDTOs.CommentWithUsernameDTO;
import com.s14_maistorbg.model.dto.commentsDTOs.ResponseCommentDTO;
import com.s14_maistorbg.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CategoryController extends ExceptionController{

    @Autowired
    CategoryService categoryService;

    @PostMapping("/repair_categories")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CategoryDTO addCategory(@RequestBody CategoryTypeDTO categoryTypeDTO, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        verifyAdminRole(request);
        return categoryService.addCategory(categoryTypeDTO);
    }

    @PutMapping("/repair_categories/{cid}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public CategoryDTO editCategory(@RequestBody CategoryTypeDTO categoryTypeDTO, @PathVariable int cid, HttpServletRequest request){
        int id = getLoggedUserId(request);
        verifyAdminRole(request);
        return categoryService.editCategory(categoryTypeDTO, cid);
    }

    @DeleteMapping("/repair_categories/{cid}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public CategoryDTO deleteCategory(@PathVariable int cid, HttpServletRequest request){
        int id = getLoggedUserId(request);
        verifyAdminRole(request);
        return categoryService.deleteCategory(cid);
    }

    @GetMapping("/repair_categories")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
