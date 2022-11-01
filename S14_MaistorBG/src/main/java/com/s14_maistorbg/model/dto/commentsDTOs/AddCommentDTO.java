package com.s14_maistorbg.model.dto.commentsDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Setter
@Getter
@NoArgsConstructor
public class AddCommentDTO {

    private int id;
    private String comment;
    private LocalDateTime commentAt;
    private int craftsmanId;
    private Optional<Integer> parentCommentId;
}
