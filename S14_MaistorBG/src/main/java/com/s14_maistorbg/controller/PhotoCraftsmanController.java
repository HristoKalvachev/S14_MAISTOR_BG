package com.s14_maistorbg.controller;

import com.s14_maistorbg.service.PhotoCraftsmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PhotoCraftsmanController extends AbstractController {

    @Autowired
    PhotoCraftsmanService photoCraftsmanService;

    @PostMapping("/craftsman/photo")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public String uploadPhoto(@RequestParam(value = "file") MultipartFile file, HttpServletRequest req) {
        int userId = getLoggedUserId(req);
        return photoCraftsmanService.uploadPhoto(userId, file);
    }

    @DeleteMapping("/craftsman/photo/{pid}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void deleteCraftsmanPhoto(@PathVariable int cid, @PathVariable int pid, HttpServletRequest req) {
        int userId = getLoggedUserId(req);
        photoCraftsmanService.deleteCraftsmanPhoto(userId, pid);
    }
}
