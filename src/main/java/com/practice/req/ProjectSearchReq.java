package com.practice.req;

import com.practice.enums.EnumProjectStatus;
import lombok.Data;

import java.util.Date;

@Data
public class ProjectSearchReq {
    private String keyword; // tìm theo tên đề tài
    private EnumProjectStatus status; // Nháp, xét duyệt, áp dụng
    private Date fromDate;
    private Date toDate;
    private int page = 0;
    private int size = 10;
}
