package com.s14_maistorbg.utility;

import com.s14_maistorbg.model.dto.offerDTOs.ResponseOfferDTO;
import com.s14_maistorbg.model.entities.Offer;
import com.s14_maistorbg.model.repositories.OfferRepository;
import com.s14_maistorbg.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
public class CronJob {

    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(cron = "0 0 0 * * *")
    public void deactivateExpiredOffers(){
        List<Offer> allOffer = getAllOffers();
        LocalDate dateNow = LocalDate.now();
        for(Offer offer : allOffer){
            if (offer.getDurationData().isBefore(dateNow)){
                offer.setClosed(true);
                sendEmail(offer.getOwner().getEmail(),
                "Expired offer",
                "Your offer " + offer.getOfferTitle() + " was deactivate.");
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void sendNoticeMailForOffer(){
        List<Offer> allOffer = getAllOffers();
        LocalDate dateAfterThreeDays = LocalDate.now().plusDays(3);
        for(Offer offer : allOffer){
            if (offer.getDurationData().isEqual(dateAfterThreeDays)){
                sendEmail(offer.getOwner().getEmail(),
                        "Notice mail for offer",
                        "Your offer will be closed at " + dateAfterThreeDays);
            }
        }
    }

    private List<Offer> getAllOffers() {
        List<Offer> offers = offerRepository.findAll();
        return offers;
    }

    @Async
    public void sendEmail(String email, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
