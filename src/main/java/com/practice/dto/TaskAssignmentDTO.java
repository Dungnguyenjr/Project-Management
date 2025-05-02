package com.practice.dto;

import com.practice.enums.TaskStatus;
import lombok.Data;

@Data
public class TaskAssignmentDTO {
    private int accountId;      // ID thực tế để POST
    private String studentCode;  // Lấy ra từ account.getStudent()
    private String fullName;     // Lấy ra từ account.getStudent()
    private TaskStatus status;
    private String note;
}
