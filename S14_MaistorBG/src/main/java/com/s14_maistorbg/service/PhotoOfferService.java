package com.s14_maistorbg.service;

import com.s14_maistorbg.model.entities.Offer;
import com.s14_maistorbg.model.entities.PhotoOffer;
import com.s14_maistorbg.model.entities.User;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.exceptions.NotFoundException;
import com.s14_maistorbg.utility.UserUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class PhotoOfferService extends AbstractService{

    @Transactional
    public String uploadOfferPhoto(int id, MultipartFile file) {
        try {
            Offer offer = offerRepository.findById(id).orElseThrow(() -> new NotFoundException("Offer not found!"));
            String ext = UserUtility.getFileExtension(file);
            String name = "images" + File.separator + System.nanoTime() + ext;
            File f = new File(name);
            if(!f.exists()) {
                Files.copy(file.getInputStream(), f.toPath());
            }
            else{
                throw new BadRequestException("This file already exists!");
            }
            PhotoOffer photoOffer = new PhotoOffer();
            photoOffer.setOffer(offer);
            photoOffer.setURl(f.getName());
            offer.getOfferPhotos().add(photoOffer);
            photoOfferRepository.save(photoOffer);
            offerRepository.save(offer);
            return name;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), e);
        }
    }
}
