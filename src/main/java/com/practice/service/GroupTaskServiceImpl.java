package com.practice.service;

import com.practice.dto.GroupTaskDTO;

import com.practice.dto.TaskAssignmentDTO;
import com.practice.entity.GroupEntity;
import com.practice.entity.GroupTask;


import com.practice.entity.TaskAssignment;
import com.practice.enums.TaskStatus;
import com.practice.repository.GroupRepository;
import com.practice.repository.GroupTaskRepository;
import com.practice.repository.TaskAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class GroupTaskServiceImpl implements GroupTaskService {

    private final GroupTaskRepository groupTaskRepo;
    private final GroupRepository groupRepo;
    private final TaskAssignmentRepository taskAssignmentRepo;
    private final TaskAssignmentService taskAssignmentService;


    @Override
    public GroupTaskDTO createGroupTask(GroupTaskDTO dto) {
        GroupEntity group = groupRepo.findById(dto.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));

        GroupTask task = new GroupTask();
        task.setTaskName(dto.getTaskName());
        task.setDescription(dto.getDescription());
        task.setStartDate(dto.getStartDate());
        task.setEndDate(dto.getEndDate());
        task.setGroup(group);
        task.setStatus(TaskStatus.NEW);

        GroupTask savedTask = groupTaskRepo.save(task);

        List<TaskAssignment> assignments = dto.getAssignments().stream()
                .map(a -> taskAssignmentService.toEntity(a, savedTask))
                .toList();

        taskAssignmentRepo.saveAll(assignments);
        savedTask.setAssignments(assignments);

        // Optionally map lại sang DTO đầy đủ
        return getGroupTaskById(savedTask.getId().intValue());
    }

    @Override
    public GroupTaskDTO getGroupTaskById(int id) {
        GroupTask task = groupTaskRepo.findById((long) id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        GroupTaskDTO dto = new GroupTaskDTO();
        dto.setId(task.getId());
        dto.setTaskName(task.getTaskName());
        dto.setStartDate(task.getStartDate());
        dto.setEndDate(task.getEndDate());
        dto.setDescription(task.getDescription());
        dto.setGroupId(task.getGroup().getId());
        dto.setStatus(task.getStatus());

        List<TaskAssignmentDTO> assignmentDTOs = task.getAssignments().stream()
                .map(taskAssignmentService::toDTO)
                .toList();

        dto.setAssignments(assignmentDTOs);
        return dto;
    }

    @Override
    public void updateStatus(Long id, TaskStatus status) {
        GroupTask task = groupTaskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(status);
        groupTaskRepo.save(task);
    }
}

