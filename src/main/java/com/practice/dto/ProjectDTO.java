package com.practice.dto;

import com.practice.entity.Criteria;
import com.practice.enums.EnumProjectStatus;
import com.practice.req.CriteriaCreateReq;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProjectDTO {
    private int id;
    private String projectName;
    private String description;
    private String content;
    private EnumProjectStatus status;
    private Date createDate;

    private List<CriteriaDTO> criteria;
}
