package com.practice.req;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GroupReportReq {
    private String title;
    private String content;
    private Long groupId;
    private Integer senderId;
    private LocalDate createdAt;
}
