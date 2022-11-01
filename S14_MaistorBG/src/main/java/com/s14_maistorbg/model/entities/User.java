package com.s14_maistorbg.model.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
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
    @JoinColumn(name = "city_id", nullable = false)
    private City city;
    @Column
    private LocalDateTime registeredAt;
    @Column
    private String profilePicUrl;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @OneToMany(mappedBy = "owner")
    private List<Offer> myOffers;
    @OneToMany(mappedBy = "commentOwner")
    private List<Comment> myComments;
    @OneToMany(mappedBy = "rater")
    private List<Rate> myGivenRates;

}
