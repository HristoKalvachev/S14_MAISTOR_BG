package com.s14_maistorbg.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String comment;
    @Column
    private LocalDate commentAt;
    @ManyToOne
    @JoinColumn(name = "comment_from")
    private User commentOwner;
    @ManyToOne
    @JoinColumn(name = "craftsman_id")
    private Craftsman craftsman;




}
