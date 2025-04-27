package com.practice.service;


import com.practice.dto.GroupMemberDetailDTO;
import com.practice.dto.GroupMemberDetailFullDTO;
import com.practice.entity.Account;
import com.practice.entity.GroupEntity;
import com.practice.entity.GroupMemberDetail;
import com.practice.repository.AccountRepository;
import com.practice.repository.GroupMemberDetailRepository;
import com.practice.repository.GroupRepository;
import com.practice.req.GroupMemberDetailReq;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class GroupMemberDetailServiceImpl implements GroupMemberDetailService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private GroupMemberDetailRepository groupMemberDetailRepository;

    @Override
    public GroupMemberDetailDTO addOrUpdateDetail(GroupMemberDetailReq req) {
        Optional<GroupEntity> groupOptional = groupRepository.findById((long) req.getGroupId());
        Optional<Account> accountOptional = accountRepository.findById(req.getStudentId());

        if (!groupOptional.isPresent()) {
            throw new IllegalArgumentException("Group not found");
        }

        if (!accountOptional.isPresent()) {
            throw new IllegalArgumentException("Student not found");
        }

        GroupEntity group = groupOptional.get();
        Account student = accountOptional.get();

        Optional<GroupMemberDetail> existingDetail = groupMemberDetailRepository.findByGroupAndStudent(group, student);
        GroupMemberDetail groupMemberDetail;

        if (existingDetail.isPresent()) {
            // Cập nhật
            groupMemberDetail = existingDetail.get();
            groupMemberDetail.setIndividualReport(req.getIndividualReport());
            groupMemberDetail.setPersonalScore(req.getPersonalScore());
            groupMemberDetail.setGroupScore(req.getGroupScore());
        } else {
            // Tạo mới
            groupMemberDetail = new GroupMemberDetail();
            groupMemberDetail.setGroup(group);
            groupMemberDetail.setStudent(student);
            groupMemberDetail.setIndividualReport(req.getIndividualReport());
            groupMemberDetail.setPersonalScore(req.getPersonalScore());
            groupMemberDetail.setGroupScore(req.getGroupScore());
        }

        groupMemberDetail = groupMemberDetailRepository.save(groupMemberDetail);

        GroupMemberDetailDTO dto = new GroupMemberDetailDTO();
        dto.setId(groupMemberDetail.getId());
        dto.setGroupId(groupMemberDetail.getGroup().getId());
        dto.setGroupName(groupMemberDetail.getGroup().getGroupName());
        dto.setStudentId(groupMemberDetail.getStudent().getId());
        dto.setStudentName(groupMemberDetail.getStudent().getFullName());
        dto.setStudentCode(groupMemberDetail.getStudent().getStudentCode());
        dto.setIndividualReport(groupMemberDetail.getIndividualReport());
        dto.setPersonalScore(groupMemberDetail.getPersonalScore());
        dto.setGroupScore(groupMemberDetail.getGroupScore());

        // Lấy giá trị enumRoleMember từ Account
        dto.setRoleMember(student.getEnumRoleMember() != null ? student.getEnumRoleMember().name() : null);  // Chỉ cần lấy trực tiếp từ Account

        return dto;
    }


    @Transactional
    public void updateGroupScore(Long groupId, Float groupScore) {
        List<GroupMemberDetail> members = groupMemberDetailRepository.findAllByGroup_Id(groupId);
        for (GroupMemberDetail member : members) {
            member.setGroupScore(groupScore);
        }
        groupMemberDetailRepository.saveAll(members);
    }

    @Override
    public List<GroupMemberDetailDTO> getDetailsByGroupId(Long groupId) {
        List<GroupMemberDetail> details = groupMemberDetailRepository.findAllByGroup_Id(groupId);

        return details.stream().map(detail -> {
            GroupMemberDetailDTO dto = new GroupMemberDetailDTO();
            dto.setId(detail.getId());
            dto.setGroupId(detail.getGroup().getId());
            dto.setGroupName(detail.getGroup().getGroupName());
            dto.setStudentId(detail.getStudent().getId());
            dto.setStudentName(detail.getStudent().getFullName());
            dto.setStudentCode(detail.getStudent().getStudentCode());
            dto.setIndividualReport(detail.getIndividualReport());
            dto.setPersonalScore(detail.getPersonalScore());
            dto.setGroupScore(detail.getGroupScore());
            // Nếu EnumRoleMember đã có giá trị, chỉ cần lấy nó
            dto.setRoleMember(detail.getStudent().getEnumRoleMember() != null ? detail.getStudent().getEnumRoleMember().name() : "No Role");




            return dto;
        }).toList();
    }
    public String uploadReportFile(Long groupMemberId, MultipartFile file) throws IOException {
        GroupMemberDetail detail = groupMemberDetailRepository.findById(groupMemberId)
                .orElseThrow(() -> new IllegalArgumentException("Group member not found"));

        String uploadDir = "uploads/";
        Files.createDirectories(Paths.get(uploadDir)); // tạo thư mục nếu chưa có

        String fileName = groupMemberId + "_" + file.getOriginalFilename();  // đặt tên tránh trùng
        Path filePath = Paths.get(uploadDir + fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Lưu đường dẫn hoặc tên file vào DB
        detail.setIndividualReport(fileName);
        groupMemberDetailRepository.save(detail);

        return fileName;
    }

    public GroupMemberDetailFullDTO getGroupMemberDetailsFull(Long groupId) {
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        GroupMemberDetailFullDTO dto = new GroupMemberDetailFullDTO();
        dto.setBatchName(group.getBatch().getName());
        dto.setBatchStartDate(group.getBatch().getDateStart());
        dto.setBatchEndDate(group.getBatch().getDateEnd());
        dto.setGroupName(group.getGroupName());
        dto.setProjectName(group.getProject().getProjectName());

        List<GroupMemberDetailFullDTO.MemberDetail> memberDetails = group.getMembers().stream().map(member -> {
            GroupMemberDetailFullDTO.MemberDetail detail = new GroupMemberDetailFullDTO.MemberDetail();
            detail.setStt(0); // tạm để 0, lát nữa set lại
            detail.setStudentCode(member.getStudent().getStudentCode());
            detail.setStudentName(member.getStudent().getFullName());
            detail.setRole(member.getStudent().getEnumRoleMember() != null ? member.getStudent().getEnumRoleMember().name() : null);
            detail.setIndividualReport(member.getIndividualReport());
            detail.setPersonalScore(member.getPersonalScore());
            detail.setGroupScore(member.getGroupScore());
            return detail;
        }).toList();

        // set STT (số thứ tự)
        for (int i = 0; i < memberDetails.size(); i++) {
            memberDetails.get(i).setStt(i + 1);
        }

        dto.setMembers(memberDetails);

        return dto;
    }



}




