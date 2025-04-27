package com.practice.dto;

import lombok.Data;

@Data
public class GroupListDTO {
    private Long id;
    private String batchName;
    private String groupName;
    private String projectName;
    private int studentCount;
}
