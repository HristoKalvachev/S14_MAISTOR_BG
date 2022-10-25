package com.s14_maistorbg.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
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
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "parent_comment_id"))
    private Comment parentComment;

}
