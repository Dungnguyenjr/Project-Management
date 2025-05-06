package com.practice.service;

import com.practice.dto.YearDTO;
import com.practice.entity.Criteria;
import com.practice.entity.Project;
import com.practice.entity.Year;
import com.practice.enums.EnumProjectStatus;
import com.practice.repository.ProjectRepository;
import com.practice.repository.YearRepository;
import com.practice.req.YearCreateReq;
import com.practice.req.YearUpdateReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class YearServiceImplTest {

    @Mock
    private YearRepository yearRepo;

    @Mock
    private ProjectRepository projectRepo;

    @InjectMocks
    private YearServiceImpl yearService;

    private Year year;
    private Project project;

    @BeforeEach
    void setUp() {
        year = new Year();
        year.setId(1); // Entity dùng int
        year.setYear(2025);

        Criteria criteria = new Criteria();
        criteria.setId(10);
        criteria.setCriteriaName("Tính sáng tạo");

        project = new Project();
        project.setId(100);
        project.setProjectName("Hệ thống quản lý");
        project.setDescription("Mô tả");
        project.setContent("Nội dung");
        project.setStatus(EnumProjectStatus.ACTIVE);
        project.setCriteria(List.of(criteria));

    }

    @Test
    void testCreateYear() {
        YearCreateReq req = new YearCreateReq();
        req.setYear(2025);
        req.setProject(List.of(100));

        when(yearRepo.save(any(Year.class))).thenReturn(year);
        when(projectRepo.findAllById(List.of(100))).thenReturn(List.of(project));
        when(projectRepo.saveAll(anyList())).thenReturn(List.of(project));

        YearDTO result = yearService.createYear(req);

        assertEquals(2025, result.getYear());
        assertEquals(1, result.getNumberProject());
        assertEquals("Hệ thống quản lý", result.getProjects().get(0).getProjectName());
        assertEquals("Tính sáng tạo", result.getProjects().get(0).getCriteria().get(0).getCriteriaName());

        verify(yearRepo).save(any(Year.class));
        verify(projectRepo).saveAll(anyList());
    }

    @Test
    void testUpdateYear() {
        YearUpdateReq req = new YearUpdateReq();
        req.setYear(2026);
        req.setProject(List.of(100));

        when(yearRepo.findById(1L)).thenReturn(Optional.of(year));
        when(yearRepo.save(any(Year.class))).thenReturn(year);
        when(projectRepo.findAllById(List.of(100))).thenReturn(List.of(project));
        when(projectRepo.saveAll(anyList())).thenReturn(List.of(project));

        YearDTO result = yearService.updateYear(1L, req);

        assertEquals(2026, result.getYear());
        assertEquals(1, result.getNumberProject());
        assertEquals("Hệ thống quản lý", result.getProjects().get(0).getProjectName());
    }

    @Test
    void testUpdateYearNotFound() {
        when(yearRepo.findById(99L)).thenReturn(Optional.empty());

        YearUpdateReq req = new YearUpdateReq();
        req.setYear(2026);
        req.setProject(List.of(100));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            yearService.updateYear(99L, req);
        });

        assertEquals("Year not found", ex.getMessage());
    }

    @Test
    void testDeleteYear() {
        doNothing().when(yearRepo).deleteById(1L);
        yearService.deleteYear(1L);
        verify(yearRepo, times(1)).deleteById(1L);
    }
}
