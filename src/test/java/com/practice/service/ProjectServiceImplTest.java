package com.practice.service;

import com.practice.dto.PageDTO;
import com.practice.dto.ProjectDTO;
import com.practice.entity.Project;
import com.practice.enums.EnumProjectStatus;
import com.practice.repository.ProjectRepository;
import com.practice.req.ProjectCreateReq;
import com.practice.req.ProjectSearchReq;
import com.practice.req.ProjectUpdateReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private Project project;
    private ProjectDTO projectDTO;
    private ProjectCreateReq projectCreateReq;
    private ProjectUpdateReq projectUpdateReq;

    @BeforeEach
    void setUp() {
        // Tạo project và projectDTO mẫu cho việc kiểm tra
        project = new Project();
        project.setProjectName("Test Project");
        project.setDescription("Test Description");
        project.setContent("Test Content");
        project.setStatus(EnumProjectStatus.ACTIVE);

        projectDTO = new ProjectDTO();
        projectDTO.setProjectName("Test Project");
        projectDTO.setDescription("Test Description");

        projectCreateReq = new ProjectCreateReq();
        projectCreateReq.setProjectName("Test Project");
        projectCreateReq.setDescription("Test Description");
        projectCreateReq.setContent("Test Content");
        projectCreateReq.setStatus(EnumProjectStatus.ACTIVE);

        projectUpdateReq = new ProjectUpdateReq();
        projectUpdateReq.setProjectName("Updated Project");
        projectUpdateReq.setDescription("Updated Description");
        projectUpdateReq.setContent("Updated Content");
        projectUpdateReq.setStatus(EnumProjectStatus.ACTIVE);
    }

    @Test
    void testCreateProject() {
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(modelMapper.map(any(Project.class), eq(ProjectDTO.class))).thenReturn(projectDTO);

        ProjectDTO createdProject = projectService.createProject(projectCreateReq);

        assertNotNull(createdProject);
        assertEquals("Test Project", createdProject.getProjectName());
        assertEquals("Test Description", createdProject.getDescription());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void testUpdateProject() {
        when(projectRepository.findById(anyInt())).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(modelMapper.map(any(Project.class), eq(ProjectDTO.class))).thenReturn(projectDTO);

        ProjectDTO updatedProject = projectService.updateProject(1, projectUpdateReq);

        assertNotNull(updatedProject);
        assertEquals("Updated Project", updatedProject.getProjectName());
        assertEquals("Updated Description", updatedProject.getDescription());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void testUpdateProjectNotFound() {
        when(projectRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            projectService.updateProject(999, projectUpdateReq); // ID không tồn tại
        });
    }

    @Test
    void testDeleteProject() {
        when(projectRepository.findById(anyInt())).thenReturn(Optional.of(project));
        doNothing().when(projectRepository).delete(any(Project.class));

        ProjectDTO deletedProject = projectService.deleteProject(1);

        assertNotNull(deletedProject);
        verify(projectRepository, times(1)).delete(any(Project.class));
    }

    @Test
    void testDeleteProjectNotFound() {
        when(projectRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            projectService.deleteProject(999); // ID không tồn tại
        });
    }

    @Test
    void testGetAllProjects() {
        List<Project> projectList = new ArrayList<>();
        projectList.add(project);

        when(projectRepository.findAll()).thenReturn(projectList);
        when(modelMapper.map(any(Project.class), eq(ProjectDTO.class))).thenReturn(projectDTO);

        List<ProjectDTO> projects = projectService.getAllProjects();

        assertNotNull(projects);
        assertEquals(1, projects.size());
        assertEquals("Test Project", projects.get(0).getProjectName());
    }

    @Test
    void testGetAllProjectsWithSearchCriteria() {
        ProjectSearchReq projectSearchReq = new ProjectSearchReq();
        projectSearchReq.setPage(0);
        projectSearchReq.setSize(10);

        List<Project> projectList = new ArrayList<>();
        projectList.add(project);

        Pageable pageable = PageRequest.of(0, 10);
        PageImpl<Project> projectPage = new PageImpl<>(projectList, pageable, projectList.size());

        // Fix lỗi ambiguous method call bằng cách chỉ rõ kiểu Specification
        when(projectRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(projectPage);
        when(modelMapper.map(any(Project.class), eq(ProjectDTO.class))).thenReturn(projectDTO);

        PageDTO<ProjectDTO> result = projectService.getAllProjects(projectSearchReq);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Test Project", result.getContent().get(0).getProjectName());
    }


}
