package com.s14_maistorbg.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "craftsman_ratings")
@Getter
@Setter
@NoArgsConstructor
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int rating;
    @ManyToOne
    @JoinColumn(name = "craftsman_id")
    private Craftsman craftsman;
    @ManyToOne
    @JoinColumn(name = "rater_id")
    private User rater;
}
