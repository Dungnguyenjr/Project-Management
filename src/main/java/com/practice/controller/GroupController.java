package com.practice.controller;

import com.practice.dto.GroupDTO;
import com.practice.req.GroupCreateReq;
import com.practice.req.GroupUpdateReq;
import com.practice.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    private boolean hasRoleTeacher() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_TEACHER"));
    }

    @PostMapping
    public ResponseEntity<?> createGroup(@RequestBody GroupCreateReq groupCreateReq) {
        if (!hasRoleTeacher()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bạn không có quyền thực hiện hành động này");
        }
        GroupDTO createdGroup = groupService.createGroup(groupCreateReq);
        return ResponseEntity.ok(createdGroup);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGroup(@PathVariable long id, @RequestBody GroupUpdateReq groupUpdateReq) {
        if (!hasRoleTeacher()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bạn không có quyền thực hiện hành động này");
        }
        GroupDTO updatedGroup = groupService.updateGroup(id, groupUpdateReq);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable int id) {
        if (!hasRoleTeacher()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bạn không có quyền thực hiện hành động này");
        }
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}
