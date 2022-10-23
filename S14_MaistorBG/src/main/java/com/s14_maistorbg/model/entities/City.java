package com.s14_maistorbg.model.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cityId;
    @Column
    private String name;
    @OneToMany(mappedBy = "city")
    private List<User> ownerID;
    @OneToMany(mappedBy = "city")
    private List<Offer> offers;

}
