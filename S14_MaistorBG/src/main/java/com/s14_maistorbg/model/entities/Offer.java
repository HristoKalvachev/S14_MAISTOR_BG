package com.s14_maistorbg.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String adType;
    //    @Column
//    private String offerDescription;
//    @Column
//    private int budget;
    @Column
    private int cityId;
    @Column
    private int repairCategoryId;
    @Column
    private int ownerId;
}
