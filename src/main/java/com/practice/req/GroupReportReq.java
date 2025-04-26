package com.practice.req;

import lombok.Data;

@Data
public class GroupReportReq {
    private String title;
    private String content;
    private Long groupId;
    private Long senderId;
}
