package com.s14_maistorbg.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(mappedBy = "myCategories")
    List<Craftsman> myCraftsmans;
}
