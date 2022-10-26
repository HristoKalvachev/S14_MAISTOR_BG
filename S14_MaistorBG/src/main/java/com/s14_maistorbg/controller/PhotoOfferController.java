package com.s14_maistorbg.controller;

import com.s14_maistorbg.service.PhotoOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PhotoOfferController {

    @Autowired
    PhotoOfferService photoOfferService;

    @PostMapping("/offer/{id}/photo")
    @ResponseStatus(code = HttpStatus.OK)
    public String uploadOfferPhoto(@PathVariable int id, @RequestParam(value = "file") MultipartFile file) {
        return photoOfferService.uploadOfferPhoto(id,file);
    }
}
