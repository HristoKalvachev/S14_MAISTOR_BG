package com.s14_maistorbg.model.dto.commentsDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CommentWithUsernameDTO {
    private int id;
    private String comment;
    private String ownerUsername;
    private int commentToCraftsmanId;
}
