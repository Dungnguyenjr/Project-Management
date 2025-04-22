package com.practice.service;
import com.practice.dto.PageDTO;
import com.practice.dto.ProjectDTO;
import com.practice.req.ProjectCreateReq;
import com.practice.req.ProjectSearchReq;

public interface ProjectService {
    ProjectDTO deleteProject(Integer id);

    ProjectDTO createProject(ProjectCreateReq projectCreateReq);

    ProjectDTO updateProject(Integer id, ProjectCreateReq projectCreateReq);

    PageDTO<ProjectDTO> getAllProjects(ProjectSearchReq projectSearchReq);
}
