package com.practice.req;

import lombok.Data;

@Data
public class GroupReportUpdateReq {
    private Long reportId;
    private String title;
    private String content;
}
