package com.practice.controller;

import com.practice.dto.GroupReportDTO;
import com.practice.req.GroupReportReq;
import com.practice.service.GroupReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group-reports")
public class GroupReportController {

    @Autowired
    private GroupReportService groupReportService;

    @PostMapping
    public GroupReportDTO createReport(@RequestBody GroupReportReq request) {
        return groupReportService.createReport(request);
    }

    @GetMapping("/group/{groupId}")
    public List<GroupReportDTO> getReportsByGroupId(@PathVariable Long groupId) {
        return groupReportService.getReportsByGroupId(groupId);
    }

    @GetMapping("/{id}")
    public GroupReportDTO getReportById(@PathVariable Long id) {
        return groupReportService.getReportById(id);
    }
}
