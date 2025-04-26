package com.practice.service;

import com.practice.dto.GroupReportDTO;
import com.practice.entity.GroupReport;
import com.practice.exception.ResourceNotFoundException;
import com.practice.mapper.GroupReportMapper;
import com.practice.repository.GroupReportRepository;
import com.practice.req.GroupReportReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupReportServiceImpl implements GroupReportService {


    @Autowired
    private GroupReportRepository reportRepository;

    @Autowired
    private GroupReportMapper groupReportMapper;

    @Override
    public GroupReportDTO createReport(GroupReportReq request) {
        GroupReport report = groupReportMapper.toEntity(request);
        report = reportRepository.save(report);
        return groupReportMapper.toDTO(report);
    }

    @Override
    public List<GroupReportDTO> getReportsByGroupId(Long groupId) {
        List<GroupReport> reports = reportRepository.findByGroup_Id(groupId);
        return reports.stream()
                .map(groupReportMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GroupReportDTO getReportById(Long id) {
        GroupReport report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found"));
        return groupReportMapper.toDTO(report);
    }
}
