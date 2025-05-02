package com.practice.controller;

import com.practice.dto.GroupMemberDetailDTO;
import com.practice.dto.GroupMemberDetailFullDTO;
import com.practice.req.GroupMemberDetailReq;
import com.practice.service.GroupMemberDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Tag(name = "GroupMemberDetailController")
@RestController
@RequestMapping("/api/group-member-details")
public class GroupMemberDetailController {

    @Autowired
    private GroupMemberDetailService groupMemberDetailService;

    @PostMapping
    public ResponseEntity<GroupMemberDetailDTO> addOrUpdateGroupMemberDetail(@RequestBody GroupMemberDetailReq req) {
        try {
            GroupMemberDetailDTO result = groupMemberDetailService.addOrUpdateDetail(req);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-group-score/{groupId}")
    public ResponseEntity<String> updateGroupScore(@PathVariable Long groupId, @RequestParam float groupScore) {
        try {
            groupMemberDetailService.updateGroupScore(groupId, groupScore);
            return new ResponseEntity<>("Update group score successfully", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error updating group score", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<GroupMemberDetailDTO>> getDetailsByGroupId(@PathVariable Long groupId) {
        List<GroupMemberDetailDTO> details = groupMemberDetailService.getDetailsByGroupId(groupId);
        return new ResponseEntity<>(details, HttpStatus.OK);
    }


    @PostMapping("/upload-report")
    public ResponseEntity<String> uploadIndividualReport(
            @RequestParam("groupMemberId") Long groupMemberId,
            @RequestParam("file") MultipartFile file) {
        try {
            String fileName = groupMemberDetailService.uploadReportFile(groupMemberId, file);
            return ResponseEntity.ok("File uploaded: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
        }
    }

    @GetMapping("/group-member-full/{groupId}")
    public ResponseEntity<GroupMemberDetailFullDTO> getGroupMemberFull(@PathVariable Long groupId) {
        GroupMemberDetailFullDTO result = groupMemberDetailService.getGroupMemberDetailsFull(groupId);
        return ResponseEntity.ok(result);
    }




}
