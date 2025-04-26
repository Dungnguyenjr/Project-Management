package com.practice.dto;

import com.practice.enums.EnumProjectStatus;

import lombok.Data;


import java.util.List;

@Data
public class ProjectDTO {
    private int id;
    private String projectName;
    private String description;
    private String content;
    private EnumProjectStatus status;

    private List<CriteriaDTO> criteria;
}
