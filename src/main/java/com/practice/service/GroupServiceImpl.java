package com.practice.service;

import com.practice.dto.*;
import com.practice.entity.Account;
import com.practice.entity.GroupEntity;
import com.practice.entity.GroupMemberDetail;
import com.practice.enums.EnumRoleMember;
import com.practice.exception.BadRequestException;
import com.practice.repository.*;
import com.practice.req.GroupCreateReq;
import com.practice.req.GroupUpdateReq;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final GroupMemberDetailRepository groupMemberDetailRepo;



    @Override
    public Page<GroupListDTO> getAllGroups(Pageable pageable) {
        return groupRepo.findAllGroupsWithBatchAndProject(pageable)
                .map(group -> {
                    GroupListDTO dto = new GroupListDTO();
                    dto.setId(group.getId());

                    if (group.getBatch() != null) {
                        String batchName = group.getBatch().getName();
                        if (group.getBatch().getDateStart() != null && group.getBatch().getDateEnd() != null) {
                            batchName += " " + group.getBatch().getDateStart() + " - " + group.getBatch().getDateEnd();
                        }
                        dto.setBatchName(batchName);
                    } else {
                        dto.setBatchName("Không có đợt");
                    }
                    dto.setGroupName(group.getGroupName() != null ? group.getGroupName() : "Không có tên nhóm");
                    if (group.getProject() != null) {
                        dto.setProjectName(group.getProject().getProjectName());
                    } else {
                        dto.setProjectName("Chưa có đề tài");
                    }
                    dto.setStudentCount(group.getStudentCount());

                    return dto;
                });
    }

    @Override
    public GroupDetailDTO getGroupDetail(Long groupId, String accountId) {
        GroupEntity group = groupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        // Lấy danh sách thành viên nhóm
        List<GroupMemberDetail> groupMembers = groupMemberDetailRepo.findByGroup_Id(groupId);

        List<GroupMemberDetailDTO> groupMemberDetailDTOS = groupMembers.stream().map(member -> {
            GroupMemberDetailDTO dto = new GroupMemberDetailDTO();
            dto.setId(member.getId());
            dto.setGroupId(groupId);
            dto.setGroupName(group.getGroupName());
            dto.setStudentId(member.getStudent().getId());
            dto.setStudentName(member.getStudent().getFullName());
            dto.setStudentCode(member.getStudent().getStudentCode());
            dto.setIndividualReport(member.getIndividualReport());
            dto.setPersonalScore(member.getPersonalScore());
            dto.setGroupScore(member.getGroupScore());
            dto.setRoleMember(
                    member.getStudent().getEnumRoleMember() != null
                            ? member.getStudent().getEnumRoleMember().name()
                            : null
            );
            return dto;
        }).collect(Collectors.toList());

        // Lấy danh sách công việc của nhóm
        List<GroupTaskDTO> taskDTOs = group.getGroupTasks().stream().map(task -> {
            GroupTaskDTO dto = new GroupTaskDTO();
            dto.setId(task.getId());
            dto.setTaskName(task.getTaskName());
            dto.setDescription(task.getDescription());
            dto.setStartDate(task.getStartDate());
            dto.setEndDate(task.getEndDate());
            dto.setGroupId(groupId);
            dto.setStatus(task.getStatus());

            List<TaskAssignmentDTO> assignments = task.getAssignments().stream().map(assign -> {
                TaskAssignmentDTO a = new TaskAssignmentDTO();
                a.setAccountId(assign.getAccount().getId());
                a.setStudentCode(assign.getAccount().getStudentCode());
                a.setFullName(assign.getAccount().getFullName());
                a.setStatus(assign.getStatus());
                a.setNote(assign.getNote());
                return a;
            }).collect(Collectors.toList());

            dto.setAssignments(assignments);
            return dto;
        }).collect(Collectors.toList());


        // Kiểm tra người dùng hiện tại có phải là trưởng nhóm không
        boolean isLeader = groupMembers.stream()
                .anyMatch(m ->
                        String.valueOf(m.getStudent().getId()).equals(accountId) &&
                                EnumRoleMember.LEADER.equals(m.getStudent().getEnumRoleMember())
                );


        // Tạo DTO trả về
        GroupDetailDTO detailDTO = new GroupDetailDTO();
        detailDTO.setGroupName(group.getGroupName());
        detailDTO.setTopicName(group.getProject().getProjectName());
        detailDTO.setBatchName(group.getBatch().getName());
        detailDTO.setStartDate(group.getBatch().getDateStart());
        detailDTO.setEndDate(group.getBatch().getDateEnd());
        detailDTO.setGroupMemberDetailDTOS(groupMemberDetailDTOS);
        detailDTO.setGroupTasks(taskDTOs);

        return detailDTO;
    }





    @Override
    public GroupDTO createGroup(GroupCreateReq groupCreateReq) {
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

//ghi nhớ
//        "pageable": {
//                "pageNumber": 0,       // Trang hiện tại (bắt đầu từ 0)
//                "pageSize": 10,        // Số phần tử mỗi trang
//                "offset": 0,           // Vị trí bắt đầu trong danh sách tổng (0 = phần tử đầu tiên)
//                "paged": true,         // Có sử dụng phân trang
//                "unpaged": false,      // Không phải là truy vấn không phân trang
//                "sort": {
//                "empty": true,       // Không sắp xếp
//                "sorted": false,
//                "unsorted": true
//                }
//                }
// cá trường ngoài "last": true,               // Đây là trang cuối cùng
//                "totalElements": 3,         // Tổng số phần tử tìm được (tổng nhóm)
//                "totalPages": 1,            // Tổng số trang (vì có 3 nhóm và pageSize = 10 => chỉ cần 1 trang)
//                "size": 10,                 // Kích thước mỗi trang (pageSize = 10)
//                "number": 0,                // Trang hiện tại (trang đầu là 0)
//                "first": true,              // Đây là trang đầu tiên
//                "numberOfElements": 3,      // Số phần tử trong trang hiện tại (3 nhóm)
//                "empty": false              // Trang này không rỗng
//