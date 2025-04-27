package com.practice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GroupMemberDetailFullDTO {
    private String batchName;
    private LocalDate batchStartDate;
    private LocalDate batchEndDate;

    private String groupName;
    private String projectName;

    private List<MemberDetail> members;

    @Data
    public static class MemberDetail {
        private int stt;
        private String studentCode;
        private String studentName;
        private String role;
        private String individualReport;
        private Float personalScore;
        private Float groupScore;
    }
}
