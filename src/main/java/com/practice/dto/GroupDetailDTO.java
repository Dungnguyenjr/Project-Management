package com.practice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GroupDetailDTO {
    private String groupName;
    private String topicName;
    private String batchName;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<GroupMemberDetailDTO> groupMemberDetailDTOS;
    private List<GroupTaskDTO> groupTasks;

}