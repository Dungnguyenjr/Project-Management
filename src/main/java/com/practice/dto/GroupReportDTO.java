package com.practice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class GroupReportDTO {
//    private Long id;
//    private String title;
//    private String content;
//    private Long groupId;
//    private Long senderId;
//    private LocalDateTime createdAt;
    private Long id;
    private String title;
    private String content;
    private Long groupId;
    private String senderName;  // Tên người gửi (Giáo viên hoặc Trưởng nhóm)
    private LocalDate createdAt;
}
