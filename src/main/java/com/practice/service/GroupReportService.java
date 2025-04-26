package com.practice.service;

import com.practice.dto.GroupReportDTO;
import com.practice.req.GroupReportReq;

import java.util.List;

public interface GroupReportService {
    GroupReportDTO createReport(GroupReportReq groupReportReq);
    List<GroupReportDTO> getReportsByGroupId(Long groupId);
    GroupReportDTO getReportById(Long id);
}

