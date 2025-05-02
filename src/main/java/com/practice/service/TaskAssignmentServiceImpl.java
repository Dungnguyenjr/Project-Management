package com.practice.service;

import com.practice.dto.TaskAssignmentDTO;
import com.practice.entity.Account;
import com.practice.entity.GroupTask;
import com.practice.entity.TaskAssignment;
import com.practice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskAssignmentServiceImpl implements TaskAssignmentService {
    @Autowired
    private final AccountRepository accountRepo;


    @Override
    public TaskAssignmentDTO toDTO(TaskAssignment assignment) {
        TaskAssignmentDTO dto = new TaskAssignmentDTO();
        dto.setAccountId(assignment.getAccount().getId());

        if (assignment.getAccount().getStudentCode() != null) {
            dto.setStudentCode(assignment.getAccount().getStudentCode());
            dto.setFullName(assignment.getAccount().getFullName());

        }

        dto.setStatus(assignment.getStatus());
        dto.setNote(assignment.getNote());

        return dto;
    }
    public TaskAssignment toEntity(TaskAssignmentDTO dto, GroupTask task) {
        Account acc = accountRepo.findById(dto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        TaskAssignment entity = new TaskAssignment();
        entity.setAccount(acc);
        entity.setStatus(dto.getStatus());
        entity.setNote(dto.getNote());
        entity.setGroupTask(task);

        return entity;
    }


}
