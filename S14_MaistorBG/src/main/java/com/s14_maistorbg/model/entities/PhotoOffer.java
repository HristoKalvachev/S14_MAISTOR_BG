package com.s14_maistorbg.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "photos_offers")
@Getter
@Setter
@NoArgsConstructor
public class PhotoOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String URl;
    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;
}
