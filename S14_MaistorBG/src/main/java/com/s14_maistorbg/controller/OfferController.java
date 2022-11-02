package com.s14_maistorbg.controller;

import com.s14_maistorbg.model.dto.offerDTOs.EditOfferDTO;
import com.s14_maistorbg.model.dto.offerDTOs.OfferDTO;
import com.s14_maistorbg.model.dto.offerDTOs.OfferWithoutOwnerDTO;
import com.s14_maistorbg.model.dto.offerDTOs.ResponseOfferDTO;
import com.s14_maistorbg.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class OfferController extends AbstractController {

    @Autowired
    private OfferService offerService;

    @PostMapping("/offers")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseOfferDTO postOffer(@RequestBody OfferDTO offerDTO, HttpServletRequest request) {
        int id = getLoggedUserId(request);
        return offerService.postOffer(offerDTO, id);
    }

    @GetMapping("/offers/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseOfferDTO findById(@PathVariable int id) {
        return offerService.findById(id);
    }

    @PutMapping("/offers/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public OfferWithoutOwnerDTO editOffer(@RequestBody EditOfferDTO editOfferDTO, @PathVariable int id, HttpServletRequest request) {
        int loggedUserId = getLoggedUserId(request);
        return offerService.editOffer(id, editOfferDTO, loggedUserId);
    }

    @DeleteMapping("/offers/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseOfferDTO deleteOffer(@PathVariable int id, HttpServletRequest request) {
        int loggedUserId = getLoggedUserId(request);
        return offerService.deleteOffer(id, loggedUserId);
    }


//    @GetMapping("/offers/craftsman/{cid}")
//    @ResponseStatus(code = HttpStatus.ACCEPTED)
//    public List<ResponseOfferDTO> getAllOffersDoneByCraftsman(@PathVariable int cid) {
//        return offerService.getAllOffersDoneByCraftsman(cid);
//    }


    @GetMapping("/offers/all")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ResponseOfferDTO> getAll(){
        return offerService.getAll();
    }

}
