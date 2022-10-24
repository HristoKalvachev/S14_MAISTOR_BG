package com.s14_maistorbg.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "repair_categories")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String type;
    @OneToMany(mappedBy = "category")
    private List<Craftsman> ownerID;
    @OneToMany(mappedBy = "repairCategoryId")
    private List<Offer> offers;
}