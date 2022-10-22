package com.s14_maistorbg.model.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String phoneNumber;
    @Column
    private String email;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City ownerID;
    @Column
    private LocalDate registeredAt;
    @Column
    private String profilePicUrl;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role roleId;
    @OneToMany(mappedBy = "owner")
    private List<Offer> myOffers;

}
