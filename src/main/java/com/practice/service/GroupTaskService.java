package com.practice.service;

import com.practice.dto.GroupTaskDTO;
import com.practice.enums.TaskStatus;

public interface GroupTaskService {
    GroupTaskDTO createGroupTask(GroupTaskDTO dto);
    GroupTaskDTO getGroupTaskById(int id);

    void updateStatus(Long id, TaskStatus status);
}
