package com.practice.service;


import com.practice.dto.GroupMemberDetailDTO;
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
            dto.setRoleMember(detail.getStudent().getEnumRoleMember() != null ? detail.getStudent().getEnumRoleMember().name() : null);


            return dto;
        }).toList();
    }

}




