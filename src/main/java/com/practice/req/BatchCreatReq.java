package com.practice.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BatchCreatReq {
    @NotEmpty(message = "year is not empty")
    private String year;

    @NotEmpty(message = "name is not empty")
    private String name;

    @JsonProperty("date_start")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateStart;

    @JsonProperty("date_end")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEnd;
}
