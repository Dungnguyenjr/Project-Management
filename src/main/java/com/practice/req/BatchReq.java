package com.practice.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BatchReq {
    @NotEmpty(message = "year is not empty")
    private String year;

    @NotEmpty(message = "name is not empty")
    private String name;

    private LocalDate dateStart;

    private LocalDate dateEnd;
}
