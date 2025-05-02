package com.practice.service;

import com.practice.dto.TaskAssignmentDTO;
import com.practice.entity.GroupTask;
import com.practice.entity.TaskAssignment;

public interface TaskAssignmentService {

    TaskAssignmentDTO toDTO(TaskAssignment assignment);
    TaskAssignment toEntity(TaskAssignmentDTO dto, GroupTask task);
}
