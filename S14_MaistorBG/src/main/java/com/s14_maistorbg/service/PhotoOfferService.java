package com.s14_maistorbg.service;

import com.s14_maistorbg.model.entities.Offer;
import com.s14_maistorbg.model.entities.PhotoOffer;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.exceptions.NotFoundException;
import com.s14_maistorbg.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class PhotoOfferService extends AbstractService {

    public String uploadOfferPhoto(int id, MultipartFile file, int userId) {
        int maxPhotos = 5;
        try {
            Offer offer = offerRepository.findById(id).orElseThrow(() -> new NotFoundException("Offer not found!"));
            if(offer.getOwner().getId()!=userId){
                throw new UnauthorizedException("You are not owner of the offer!");
            }
            if (offer.getOfferPhotos().size() >= maxPhotos) {
                throw new UnauthorizedException("You can add maximum " + maxPhotos + " pictures!");
            }
            String name = createFileAndReturnName(file, id);

            PhotoOffer photoOffer = new PhotoOffer();
            photoOffer.setOffer(offer);
            photoOffer.setURl(name);
            photoOfferRepository.save(photoOffer);
            return name;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), e);
        }
    }

    public void deleteOfferPhoto(int oid, int pid, int userId) {
        Offer offer =getOfferById(oid);
        if(offer.getOwner().getId()!=userId){
            throw new UnauthorizedException("You are not owner of the offer!");
        }
        PhotoOffer photoOffer = photoOfferRepository.findById(pid).orElseThrow(() -> new NotFoundException("Photo not found!"));
        File file = new File("images" + File.separator + photoOffer.getURl());
        if (file.delete()) {
            photoOfferRepository.delete(photoOffer);
        } else {
            throw new BadRequestException("Can not delete photo!");
        }
    }
}
