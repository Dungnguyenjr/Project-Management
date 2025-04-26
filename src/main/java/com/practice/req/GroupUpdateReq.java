package com.practice.req;

import com.practice.dto.StudentDTO;
import lombok.Data;

import java.util.List;
@Data
public class GroupUpdateReq {
    private long id;
    private long batchId;
    private String batchName;

    private String groupName;

    private int projectId;
    private String projectName;

    private List<StudentDTO> students; // Chỉ chứa các sinh viên được chọn
    private int leaderId;
}
