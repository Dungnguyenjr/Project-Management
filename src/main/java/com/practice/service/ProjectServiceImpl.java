package com.practice.service;

import com.practice.dto.PageDTO;
import com.practice.dto.ProjectDTO;
import com.practice.entity.Criteria;
import com.practice.entity.Project;
import com.practice.repository.ProjectRepository;
import com.practice.req.CriteriaCreateReq;
import com.practice.req.ProjectCreateReq;
import com.practice.req.ProjectSearchReq;
import com.practice.specification.ProjectSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;



import java.util.List;
import java.util.Objects;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProjectDTO createProject(ProjectCreateReq projectCreateReq) {
        // Tạo mới dự án
        Project project = new Project();
        project.setProjectName(projectCreateReq.getProjectName());
        project.setDescription(projectCreateReq.getDescription());
        project.setContent(projectCreateReq.getContent());

        // Lặp qua danh sách Criteria và thêm pr project
        for (CriteriaCreateReq criteriaCreateReq : projectCreateReq.getCriteria()) {
            Criteria newCrit = new Criteria();
            newCrit.setCriteriaName(criteriaCreateReq.getCriteriaName());
            newCrit.setProject(project);
            project.getCriteria().add(newCrit);
        }

        Project savedProject = projectRepository.save(project);
        return modelMapper.map(savedProject, ProjectDTO.class);
    }

    @Override
    public ProjectDTO updateProject(Integer id, ProjectCreateReq projectCreateReq) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setProjectName(projectCreateReq.getProjectName());
        project.setDescription(projectCreateReq.getDescription());
        project.setContent(projectCreateReq.getContent());

        // Lấy các ID Criteria đã gửi lên
        List<Integer> incomingIds = projectCreateReq.getCriteria().stream()
                .map(CriteriaCreateReq::getId)
                .filter(Objects::nonNull)
                .toList();

        // Xóa các tiêu chí không còn trong danh sách gửi lên
        project.getCriteria().removeIf(existing -> existing.getId() != null && !incomingIds.contains(existing.getId()));


        for (CriteriaCreateReq criteriaCreateReq : projectCreateReq.getCriteria()) {
            if (criteriaCreateReq.getId() == null) {

                Criteria newCrit = new Criteria();
                newCrit.setCriteriaName(criteriaCreateReq.getCriteriaName());
                newCrit.setProject(project);
                project.getCriteria().add(newCrit);
            } else {
                project.getCriteria().stream()
                        .filter(c -> c.getId().equals(criteriaCreateReq.getId()))
                        .findFirst()
                        .ifPresent(c -> c.setCriteriaName(criteriaCreateReq.getCriteriaName()));
            }
        }

        // Lưu lại thay đổi
        Project updatedProject = projectRepository.save(project);
        return modelMapper.map(updatedProject, ProjectDTO.class);
    }




    @Override
    public ProjectDTO deleteProject(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        // Xóa các tiêu chí liên quan (nếu có)
        project.getCriteria().clear();

        // Xóa đề tài
        projectRepository.delete(project);

        return modelMapper.map(project, ProjectDTO.class);
    }


    @Override
    public PageDTO<ProjectDTO> getAllProjects(ProjectSearchReq projectSearchReq) {
        Pageable pageable = PageRequest.of(projectSearchReq.getPage(), projectSearchReq.getSize(), Sort.by("createDate").descending());
        Page<Project> page = projectRepository.findAll(ProjectSpecification.filter(projectSearchReq), pageable);

        List<ProjectDTO> content = page.getContent()
                .stream()
                .map(project -> modelMapper.map(project, ProjectDTO.class))
                .toList();

        PageDTO<ProjectDTO> result = new PageDTO<>();
        result.setContent(content);
        result.setPageNumber(page.getNumber());
        result.setPageSize(page.getSize());
        result.setTotalElements(page.getTotalElements());
        result.setTotalPages(page.getTotalPages());
        result.setLast(page.isLast());
        return result;
    }


}
