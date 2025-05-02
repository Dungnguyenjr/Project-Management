package com.practice.controller;

import com.practice.dto.GroupDetailDTO;
import com.practice.dto.GroupListDTO;
import com.practice.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student/groups")
public class StudentGroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    public Page<GroupListDTO> getGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return groupService.getAllGroups(PageRequest.of(page, size));
    }

    @GetMapping("/{groupId}/detail")
    public GroupDetailDTO getGroupDetail(
            @PathVariable Long groupId,
            @RequestParam String accountId) {
        return groupService.getGroupDetail(groupId, accountId);
    }


}
