package com.s14_maistorbg.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private String jobDescription;
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
    @Column
    private boolean isClosed;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime durationData;
    @OneToMany(mappedBy = "offer")
    private List<PhotoOffer> offerPhotos;
    @OneToMany(mappedBy = "appliedOffer")
    private List<ApplicationForOffer> applicationsForOffer;
    @ManyToOne
    @JoinColumn(name = "selected_application_id")
    private ApplicationForOffer selectedApplication;

}
