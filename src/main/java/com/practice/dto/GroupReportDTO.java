package com.practice.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class GroupReportDTO {
    private Long id;
    private String title;
    private String content;
    private Long groupId;
    private Long senderId;
    private LocalDateTime createdAt;
}
