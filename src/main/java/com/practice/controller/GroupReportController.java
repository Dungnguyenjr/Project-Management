package com.practice.controller;

import com.practice.dto.GroupReportDTO;
import com.practice.req.GroupReportReq;
import com.practice.service.GroupReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group-reports")
public class GroupReportController {

    @Autowired
    private GroupReportService groupReportService;

    private boolean hasRoleStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_STUDENT"));
    }

    @PostMapping
    public ResponseEntity<?> createReport(@RequestBody GroupReportReq request) {
        if (!hasRoleStudent()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bạn không có quyền thực hiện hành động này");
        }
        GroupReportDTO createdReport = groupReportService.createReport(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<GroupReportDTO>> getReportsByGroupId(@PathVariable Long groupId) {
        List<GroupReportDTO> reports = groupReportService.getReportsByGroupId(groupId);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupReportDTO> getReportById(@PathVariable Long id) {
        GroupReportDTO report = groupReportService.getReportById(id);
        return ResponseEntity.ok(report);
    }
}
