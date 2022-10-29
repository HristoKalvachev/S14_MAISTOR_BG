package com.s14_maistorbg.service;

import com.s14_maistorbg.model.entities.Craftsman;
import com.s14_maistorbg.model.entities.Offer;
import com.s14_maistorbg.model.entities.PhotoCraftsman;
import com.s14_maistorbg.model.entities.PhotoOffer;
import com.s14_maistorbg.model.exceptions.BadRequestException;
import com.s14_maistorbg.model.exceptions.NotFoundException;
import com.s14_maistorbg.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class PhotoCraftsmanService extends AbstractService{

    public String uploadPhoto(int id, MultipartFile file) {
        try {
            Craftsman craftsman = getCraftsmanById(id);
            if (craftsman.getMyPhotos().size() >= 5) {
                throw new UnauthorizedException("You can add maximum 5 pictures!");
            }
            String name = createFileAndReturnName(file);

            PhotoCraftsman photoCraftsman = new PhotoCraftsman();
            photoCraftsman.setCraftsman(craftsman);
            photoCraftsman.setURL(name);
            photoCraftsmanRepository.save(photoCraftsman);
            return name;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), e);
        }
    }

    public void deleteOfferPhoto(int cid, int pid) {
        Craftsman craftsman = getCraftsmanById(cid);
        PhotoCraftsman photoCraftsman = photoCraftsmanRepository.findById(pid).orElseThrow(() -> new NotFoundException("Photo not found!"));
        File file = new File("images"+File.separator+photoCraftsman.getURL());
        if(file.delete()){
            photoCraftsmanRepository.delete(photoCraftsman);
        }else {
            throw new BadRequestException("Can not delete photo!");
        }
    }
}
