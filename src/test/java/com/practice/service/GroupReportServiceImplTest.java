package com.practice.service;

import com.practice.dto.GroupReportDTO;
import com.practice.entity.GroupReport;
import com.practice.exception.ResourceNotFoundException;
import com.practice.mapper.GroupReportMapper;
import com.practice.repository.GroupReportRepository;
import com.practice.req.GroupReportReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GroupReportServiceImplTest {
    @InjectMocks
    private GroupReportServiceImpl groupReportService;

    @Mock
    private GroupReportRepository reportRepository;

    @Mock
    private GroupReportMapper groupReportMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReport() {
        GroupReportReq req = new GroupReportReq();
        req.setTitle("Final Report");
        req.setContent("Report Content");
        req.setGroupId(1L);

        GroupReport reportEntity = new GroupReport();
        reportEntity.setTitle("Final Report");

        GroupReport savedEntity = new GroupReport();
        savedEntity.setId(1L);
        savedEntity.setTitle("Final Report");

        GroupReportDTO dto = new GroupReportDTO();
        dto.setId(1L);
        dto.setTitle("Final Report");

        when(groupReportMapper.toEntity(req)).thenReturn(reportEntity);
        when(reportRepository.save(reportEntity)).thenReturn(savedEntity);
        when(groupReportMapper.toDTO(savedEntity)).thenReturn(dto);

        GroupReportDTO result = groupReportService.createReport(req);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Final Report", result.getTitle());
    }

    @Test
    void testGetReportsByGroupId() {
        Long groupId = 1L;

        GroupReport report1 = new GroupReport();
        report1.setId(1L);
        report1.setTitle("Report 1");

        GroupReport report2 = new GroupReport();
        report2.setId(2L);
        report2.setTitle("Report 2");

        GroupReportDTO dto1 = new GroupReportDTO();
        dto1.setId(1L);
        dto1.setTitle("Report 1");

        GroupReportDTO dto2 = new GroupReportDTO();
        dto2.setId(2L);
        dto2.setTitle("Report 2");

        when(reportRepository.findByGroup_Id(groupId)).thenReturn(Arrays.asList(report1, report2));
        when(groupReportMapper.toDTO(report1)).thenReturn(dto1);
        when(groupReportMapper.toDTO(report2)).thenReturn(dto2);

        List<GroupReportDTO> result = groupReportService.getReportsByGroupId(groupId);

        assertEquals(2, result.size());
        assertEquals("Report 1", result.get(0).getTitle());
        assertEquals("Report 2", result.get(1).getTitle());
    }

    @Test
    void testGetReportById_Success() {
        GroupReport report = new GroupReport();
        report.setId(1L);
        report.setTitle("Midterm");

        GroupReportDTO dto = new GroupReportDTO();
        dto.setId(1L);
        dto.setTitle("Midterm");

        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));
        when(groupReportMapper.toDTO(report)).thenReturn(dto);

        GroupReportDTO result = groupReportService.getReportById(1L);

        assertNotNull(result);
        assertEquals("Midterm", result.getTitle());
    }

    @Test
    void testGetReportById_NotFound() {
        when(reportRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> groupReportService.getReportById(100L));
    }
}
