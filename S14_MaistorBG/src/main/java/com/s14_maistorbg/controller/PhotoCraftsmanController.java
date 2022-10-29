package com.s14_maistorbg.controller;

import com.s14_maistorbg.service.PhotoCraftsmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PhotoCraftsmanController extends ExceptionController{

    @Autowired
    PhotoCraftsmanService photoCraftsmanService;

    @PostMapping("/craftsman/{id}/photo")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public String uploadPhoto (@PathVariable int id, @RequestParam(value = "file") MultipartFile file){
        return photoCraftsmanService.uploadPhoto(id,file);
    }

    @DeleteMapping("/craftsman/{cid}/photo/{pid}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void deleteOfferPhoto(@PathVariable int cid, @PathVariable int pid){
        photoCraftsmanService.deleteOfferPhoto(cid,pid);
    }
}
