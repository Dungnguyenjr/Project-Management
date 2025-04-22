package com.practice.req;

import com.practice.enums.EnumProjectStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProjectCreateReq {
    private String projectName;
    private String description;
    private String content;

    private EnumProjectStatus status;

    private Date createDate;
    private List<CriteriaCreateReq> criteria;
}
