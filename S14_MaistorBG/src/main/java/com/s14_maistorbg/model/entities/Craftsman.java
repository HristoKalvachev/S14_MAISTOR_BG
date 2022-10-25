package com.s14_maistorbg.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


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

}
