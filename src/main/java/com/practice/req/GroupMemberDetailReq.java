package com.practice.req;

import lombok.Data;

@Data
public class GroupMemberDetailReq {

    private int groupId;  // Group ID
    private int studentId;  // Student ID
    private String individualReport;  // Báo cáo cá nhân
    private float personalScore;  // Điểm cá nhân
    private float groupScore;  // Điểm nhóm

}
