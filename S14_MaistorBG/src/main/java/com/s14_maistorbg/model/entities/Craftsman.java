package com.s14_maistorbg.model.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "craftsman")
public class Craftsman {

    @Id
    private int userId;
    @Column
    private int rating;
    @Column
    private int numberUsersRated;
    @Column
    private int repairCategoryId;



}
