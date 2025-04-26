package com.practice.dto;


import lombok.Data;

import java.util.List;

@Data
public class YearDTO {
    private int id;
    private Integer year;
    private int numberProject;
    private List<ProjectDTO> projects;
}

