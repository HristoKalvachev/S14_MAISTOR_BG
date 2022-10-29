package com.s14_maistorbg.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "offer_applications")
@Getter
@Setter
@NoArgsConstructor
public class ApplicationForOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int price;
    @Column
    private int deadline;
    @Column
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer appliedOffer;
    @ManyToOne
    @JoinColumn(name = "craftsman_id")
    private Craftsman craftsman;

}
