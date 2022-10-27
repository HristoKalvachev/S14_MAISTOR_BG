package com.s14_maistorbg.model.entities;


import lombok.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "craftsman")
@Getter
@Setter
@NoArgsConstructor
public class Craftsman {

    @Id
    @Column

    private int userId;
    @ManyToOne
    @JoinColumn(name = "repair_category_id")

    private Category category;
    @OneToMany(mappedBy = "craftsman")

    private List<Comment> myAccountComments;
    @OneToMany(mappedBy = "craftsman")

    private List<Rate> myRatesReceived;
    @OneToMany(mappedBy = "craftsman")
    private List<PhotoCraftsman> myPhotos;


    @ManyToMany
    @JoinTable(
            name = "craftsmans_categories",
            joinColumns = @JoinColumn(name = "craftsman_id"),
            inverseJoinColumns = @JoinColumn(name = "repair_categories_id"))
    List<Category> myCategories;

    @OneToMany(mappedBy = "craftsman")
    private List<ApplicationForOffer> applications;
}
