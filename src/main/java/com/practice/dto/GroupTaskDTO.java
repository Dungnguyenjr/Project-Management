package com.practice.dto;


import com.practice.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GroupTaskDTO {

    private Long id;
    private String taskName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Long groupId;
    private List<TaskAssignmentDTO> assignments;
    private TaskStatus status;
}
