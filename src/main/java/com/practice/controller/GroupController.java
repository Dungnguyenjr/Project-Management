package com.practice.controller;

import com.practice.dto.GroupDTO;
import com.practice.req.GroupCreateReq;
import com.practice.req.GroupUpdateReq;
import com.practice.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

//    @GetMapping
//    public List<GroupDTO> getAllGroups() {
//        // logic lấy danh sách nhóm
//    }

    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(@RequestBody GroupCreateReq groupCreateReq) {
        GroupDTO createdGroup = groupService.createGroup(groupCreateReq);
        return ResponseEntity.ok(createdGroup);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDTO> updateGroup(@PathVariable long id, @RequestBody GroupUpdateReq groupUpdateReq) {
        GroupDTO updatedGroup = groupService.updateGroup(id, groupUpdateReq);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable int id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

}

