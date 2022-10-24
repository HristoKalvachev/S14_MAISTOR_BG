package com.s14_maistorbg.model.dto.commentsDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class EditCommentDTO {

    private int commentId;
    private String comment;


}
