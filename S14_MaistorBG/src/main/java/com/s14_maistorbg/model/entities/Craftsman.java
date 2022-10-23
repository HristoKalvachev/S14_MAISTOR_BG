package com.s14_maistorbg.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "craftsman")
public class Craftsman {

    @Id
    @Column
    private int id;
    @Column
    private int rating;
    @Column
    private int numberUsersRated;
    @ManyToOne
    @JoinColumn(name = "repair_category_id")
    private Category category;

}
