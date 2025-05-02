package com.practice.controller;


import com.practice.dto.GroupTaskDTO;
import com.practice.enums.TaskStatus;
import com.practice.req.TaskStatusRequest;
import com.practice.service.GroupTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/group-task")
@RequiredArgsConstructor
public class GroupTaskController {

    private final GroupTaskService groupTaskService;

    @PostMapping
    public ResponseEntity<?> createGroupTask(@RequestBody GroupTaskDTO groupTaskDTO) {
        GroupTaskDTO created = groupTaskService.createGroupTask(groupTaskDTO);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGroupTask(@PathVariable int id) {
        GroupTaskDTO dto = groupTaskService.getGroupTaskById(id);
        return ResponseEntity.ok(dto);
    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateGroupTaskStatus(@PathVariable Long id, @RequestBody TaskStatusRequest request) {
        try {
            groupTaskService.updateStatus(id, request.getStatus());
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Cập nhật trạng thái thành công",
                    "status", request.getStatus().name()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", "Cập nhật trạng thái thất bại: " + e.getMessage()
            ));
        }
    }




}
