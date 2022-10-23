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


}
