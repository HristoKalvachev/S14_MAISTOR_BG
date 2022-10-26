package com.s14_maistorbg.model.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ExceptionDTO {

    private int status;
    private LocalDateTime dateTime;
    private String msg;
}
