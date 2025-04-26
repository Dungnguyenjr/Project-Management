package com.practice.dto;


import lombok.Data;

import java.util.List;

@Data
public class GroupDTO {
    private long id;
    private int batchId;
    private String batchName;
    private String groupName;
    private int projectId;
    private String projectName;

    private List<StudentGroupDTO> students; // Chỉ chứa thông tin cần thiết
    private int leaderId;
    private Integer studentCount;


}
