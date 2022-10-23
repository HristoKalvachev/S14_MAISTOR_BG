package com.s14_maistorbg.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "craftsman_ratings")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int rating;
    @Column
    private int craftsmanId;
    @Column
    private int raterId;
}
