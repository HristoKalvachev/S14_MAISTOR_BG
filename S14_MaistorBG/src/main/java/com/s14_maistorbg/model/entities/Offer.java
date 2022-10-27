package com.s14_maistorbg.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "offers")
@Getter
@Setter
@NoArgsConstructor
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String offerTitle;
    @Column
    private String jobDecscription;
    @Column
    private double budget;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @ManyToOne
    @JoinColumn(name = "repair_category_id")
    private Category repairCategoryId;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @OneToMany(mappedBy = "offer")
    private List<PhotoOffer> offerPhotos;
    @OneToMany(mappedBy = "appliedOffer")
    private List<ApplicationForOffer> applicationsForOffer;
    @OneToOne(mappedBy = "selectedOffer")
    private ApplicationForOffer selectedApplication;



}
