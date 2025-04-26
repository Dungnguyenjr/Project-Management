package com.practice.service;

import com.practice.dto.GroupDTO;
import com.practice.dto.StudentDTO;
import com.practice.dto.StudentGroupDTO;
import com.practice.entity.Account;
import com.practice.entity.GroupEntity;
import com.practice.entity.GroupMemberDetail;
import com.practice.enums.EnumRoleMember;
import com.practice.exception.BadRequestException;
import com.practice.repository.AccountRepository;
import com.practice.repository.BatchRepository;
import com.practice.repository.GroupRepository;
import com.practice.repository.ProjectRepository;
import com.practice.req.GroupCreateReq;
import com.practice.req.GroupUpdateReq;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepo;
    private final BatchRepository batchRepo;
    private final ProjectRepository projectRepo;
    private final AccountRepository accountRepo;
    private final ModelMapper modelMapper;




    @Override
    public GroupDTO createGroup(GroupCreateReq groupCreateReq) {
        // Tạo đối tượng GroupEntity
        GroupEntity group = new GroupEntity();
        group.setGroupName(groupCreateReq.getGroupName());
        group.setBatch(batchRepo.findById(groupCreateReq.getBatchId()).orElseThrow());
        group.setProject(projectRepo.findById(groupCreateReq.getProjectId()).orElseThrow());

        // Lấy danh sách ID sinh viên từ request
        List<Integer> studentIds = groupCreateReq.getStudents()
                .stream()
                .map(StudentDTO::getId)
                .collect(Collectors.toList());

        // Lấy danh sách account theo ID
        List<Account> accounts = accountRepo.findAllById(studentIds);

        // Dùng AtomicBoolean để thay đổi giá trị trong lambda
        AtomicBoolean leaderFound = new AtomicBoolean(false);

        // Tạo danh sách thành viên nhóm (group_member_detail)
        List<GroupMemberDetail> members = accounts.stream().map(account -> {
            if (account.getEnumRoleMember() != null) {
                throw new BadRequestException("Sinh viên " + account.getFullName() + " đã thuộc nhóm khác.");
            }

            GroupMemberDetail detail = new GroupMemberDetail();
            detail.setGroup(group);
            detail.setStudent(account);

            if (account.getId() == groupCreateReq.getLeaderId()) {
                account.setEnumRoleMember(EnumRoleMember.LEADER);
                leaderFound.set(true);  // Sử dụng AtomicBoolean để thay đổi giá trị
            } else {
                account.setEnumRoleMember(EnumRoleMember.MEMBER);
            }

            return detail;
        }).collect(Collectors.toList());

        if (!leaderFound.get()) {
            throw new BadRequestException("LeaderId không nằm trong danh sách sinh viên.");
        }

        group.setMembers(members);

        GroupEntity savedGroup = groupRepo.save(group);

        // Convert sang DTO
        GroupDTO dto = modelMapper.map(savedGroup, GroupDTO.class);
        List<StudentGroupDTO> studentDTOs = savedGroup.getMembers()
                .stream()
                .map(detail -> {
                    StudentGroupDTO sDto = new StudentGroupDTO();
                    sDto.setId(detail.getStudent().getId());
                    sDto.setFullName(detail.getStudent().getFullName());
                    sDto.setEnumRoleMember(detail.getStudent().getEnumRoleMember());
                    return sDto;
                }).collect(Collectors.toList());

        dto.setStudents(studentDTOs);
        dto.setStudentCount(studentDTOs.size());
        dto.setLeaderId(groupCreateReq.getLeaderId());

        return dto;
    }



    @Override
    public GroupDTO updateGroup(long id, GroupUpdateReq groupUpdateReq) {
        // Tìm nhóm theo id
        GroupEntity group = groupRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Group not found"));

        // Cập nhật thông tin nhóm
        group.setGroupName(groupUpdateReq.getGroupName());
        group.setBatch(batchRepo.findById(groupUpdateReq.getBatchId()).orElseThrow(() -> new IllegalArgumentException("Batch not found")));
        group.setProject(projectRepo.findById(groupUpdateReq.getProjectId()).orElseThrow(() -> new IllegalArgumentException("Project not found")));

        // Cập nhật danh sách sinh viên
        List<Integer> studentIds = groupUpdateReq.getStudents()
                .stream()
                .map(StudentDTO::getId)
                .collect(Collectors.toList());

        List<Account> accounts = accountRepo.findAllById(studentIds);

        // Dùng AtomicBoolean để thay đổi giá trị trong lambda
        AtomicBoolean leaderFound = new AtomicBoolean(false);

        // Tạo danh sách thành viên nhóm (group_member_detail)
        List<GroupMemberDetail> members = accounts.stream().map(account -> {
            if (account.getEnumRoleMember() != null) {
                throw new BadRequestException("Sinh viên " + account.getFullName() + " đã thuộc nhóm khác.");
            }

            GroupMemberDetail detail = new GroupMemberDetail();
            detail.setGroup(group);
            detail.setStudent(account);

            if (account.getId() == groupUpdateReq.getLeaderId()) {
                account.setEnumRoleMember(EnumRoleMember.LEADER);
                leaderFound.set(true);  // Dùng AtomicBoolean để thay đổi giá trị
            } else {
                account.setEnumRoleMember(EnumRoleMember.MEMBER);
            }

            return detail;
        }).collect(Collectors.toList());

        if (!leaderFound.get()) {
            throw new IllegalArgumentException("LeaderId không nằm trong danh sách sinh viên.");
        }

        group.setMembers(members);

        // Lưu lại nhóm đã sửa
        GroupEntity savedGroup = groupRepo.save(group);

        // Chuyển đổi sang DTO
        GroupDTO dto = modelMapper.map(savedGroup, GroupDTO.class);

        List<StudentGroupDTO> studentDTOs = savedGroup.getMembers()
                .stream()
                .map(detail -> {
                    StudentGroupDTO sDto = new StudentGroupDTO();
                    sDto.setId(detail.getStudent().getId());
                    sDto.setFullName(detail.getStudent().getFullName());
                    sDto.setEnumRoleMember(detail.getStudent().getEnumRoleMember());
                    return sDto;
                }).collect(Collectors.toList());

        dto.setStudents(studentDTOs);
        dto.setStudentCount(studentDTOs.size());
        dto.setLeaderId(groupUpdateReq.getLeaderId());

        return dto;
    }


    @Override
    public void deleteGroup(long id) {
        // Tìm nhóm theo ID
        GroupEntity group = groupRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Group not found"));

        // Xóa nhóm (vẫn giữ lại các account liên kết)
        groupRepo.delete(group);
    }



}

