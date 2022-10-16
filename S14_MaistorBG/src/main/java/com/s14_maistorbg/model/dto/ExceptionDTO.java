package com.s14_maistorbg.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExceptionDTO {

    private int status;
    private LocalDateTime dateTime;
    private String msg;
}
