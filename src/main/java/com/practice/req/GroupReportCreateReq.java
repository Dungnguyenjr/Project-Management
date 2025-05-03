package com.practice.req;

import lombok.Data;

@Data
public class GroupReportCreateReq {
    private Long groupId;
    private String title;
    private String content;
}
