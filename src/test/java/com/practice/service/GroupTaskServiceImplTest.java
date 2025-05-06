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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GroupTaskServiceImplTest {

    @InjectMocks
    private GroupTaskServiceImpl groupTaskService;

    @Mock
    private GroupTaskRepository groupTaskRepo;

    @Mock
    private GroupRepository groupRepo;

    @Mock
    private TaskAssignmentRepository taskAssignmentRepo;

    @Mock
    private TaskAssignmentService taskAssignmentService;

    private GroupEntity group;
    private GroupTaskDTO dto;

    @BeforeEach
    void setUp() {
        group = new GroupEntity();
        group.setId(1L);

        TaskAssignmentDTO assignment = new TaskAssignmentDTO();
        assignment.setAccountId(1);
        assignment.setNote("Ghi chú");
        assignment.setStatus(TaskStatus.NEW);


        dto = new GroupTaskDTO();
        dto.setTaskName("Thiết kế hệ thống");
        dto.setDescription("Thiết kế sơ đồ tổng quan");
        dto.setStartDate(LocalDate.now());
        dto.setEndDate(LocalDate.now().plusDays(5));
        dto.setGroupId(1L);
        dto.setAssignments(List.of(assignment));
    }

    @Test
    void testCreateGroupTask_success() {
        // Prepare input DTO
        GroupTaskDTO dto = new GroupTaskDTO();
        dto.setTaskName("Thiết kế hệ thống");
        dto.setDescription("Mô tả...");
        dto.setStartDate(LocalDate.now());
        dto.setEndDate(LocalDate.now().plusDays(7));
        dto.setGroupId(1L);

        TaskAssignmentDTO assignmentDTO = new TaskAssignmentDTO();
        assignmentDTO.setAccountId(1);
        assignmentDTO.setNote("Note here");
        assignmentDTO.setStatus(TaskStatus.NEW);

        dto.setAssignments(List.of(assignmentDTO));

        // Prepare mock group
        GroupEntity group = new GroupEntity();
        group.setId(1L);

        GroupTask savedTask = new GroupTask();
        savedTask.setId(10L);
        savedTask.setTaskName(dto.getTaskName());
        savedTask.setGroup(group);
        savedTask.setStatus(TaskStatus.NEW);
        savedTask.setAssignments(List.of());

        TaskAssignment mockAssignment = new TaskAssignment();

        // Mock repository/service behavior
        when(groupRepo.findById(1L)).thenReturn(Optional.of(group));
        when(groupTaskRepo.save(any(GroupTask.class))).thenReturn(savedTask);
        when(taskAssignmentService.toEntity(any(), any())).thenReturn(mockAssignment);
        when(taskAssignmentRepo.saveAll(any())).thenReturn(List.of(mockAssignment));
        when(groupTaskRepo.findById(10L)).thenReturn(Optional.of(savedTask));
        when(taskAssignmentService.toDTO(any())).thenReturn(new TaskAssignmentDTO());

        // Call service method
        GroupTaskDTO result = groupTaskService.createGroupTask(dto);

        // Assert
        assertNotNull(result);
        assertEquals("Thiết kế hệ thống", result.getTaskName());
        verify(groupTaskRepo, times(1)).save(any());
        verify(taskAssignmentRepo, times(1)).saveAll(any());
    }

    @Test
    void testGetGroupTaskById_success() {
        GroupTask task = new GroupTask();
        task.setId(10L);
        task.setTaskName("Test task");
        task.setDescription("Test desc");
        task.setStartDate(LocalDate.now());
        task.setEndDate(LocalDate.now().plusDays(1));
        task.setGroup(group);
        task.setStatus(TaskStatus.NEW);
        task.setAssignments(List.of(new TaskAssignment()));

        when(groupTaskRepo.findById(10L)).thenReturn(Optional.of(task));
        when(taskAssignmentService.toDTO(any())).thenReturn(new TaskAssignmentDTO());

        GroupTaskDTO result = groupTaskService.getGroupTaskById(10);

        assertEquals("Test task", result.getTaskName());
        assertEquals(TaskStatus.NEW, result.getStatus());
        assertEquals(1, result.getAssignments().size());
    }

    @Test
    void testUpdateStatus_success() {
        GroupTask task = new GroupTask();
        task.setId(99L);
        task.setStatus(TaskStatus.NEW);

        when(groupTaskRepo.findById(99L)).thenReturn(Optional.of(task));

        groupTaskService.updateStatus(99L, TaskStatus.DONE);

        assertEquals(TaskStatus.DONE, task.getStatus());
        verify(groupTaskRepo).save(task);
    }

    @Test
    void testCreateGroupTask_groupNotFound_throwsException() {
        when(groupRepo.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                groupTaskService.createGroupTask(dto));

        assertEquals("Group not found", exception.getMessage());
    }

    @Test
    void testGetGroupTaskById_notFound_throwsException() {
        when(groupTaskRepo.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                groupTaskService.getGroupTaskById(99));

        assertEquals("Task not found", ex.getMessage());
    }
}
