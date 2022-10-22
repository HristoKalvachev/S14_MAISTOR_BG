package com.s14_maistorbg.model.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;
    @Column
    private String role_type;
    @OneToMany(mappedBy = "roleOwner")
    private List<User> ownerID;
}
