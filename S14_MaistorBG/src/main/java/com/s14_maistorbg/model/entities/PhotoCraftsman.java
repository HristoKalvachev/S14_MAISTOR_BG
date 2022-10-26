package com.s14_maistorbg.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "photos_craftsman")
@Getter
@Setter
@NoArgsConstructor
public class PhotoCraftsman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String URL;
    @ManyToOne
    @JoinColumn(name = "craftsman_id")
    private Craftsman craftsman;
}
