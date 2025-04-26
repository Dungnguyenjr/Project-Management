package com.practice.dto;

import lombok.Data;

@Data
public class GroupMemberDetailDTO {
    private Long id;
    private Long groupId;
    private String groupName;      // thêm tên nhóm
    private int studentId;
    private String studentName;    // thêm tên sinh viên
    private String studentCode;    // thêm mã sinh viên
    private String individualReport;
    private Float personalScore;
    private Float groupScore;
    private String roleMember; // để trả ra dạng "LEADER" hoặc "MEMBER"

//    private EnumRoleMember roleMember; // kiểu enum luôn


}
