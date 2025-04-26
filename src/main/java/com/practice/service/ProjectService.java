package com.practice.service;
import com.practice.dto.PageDTO;
import com.practice.dto.ProjectDTO;
import com.practice.req.ProjectCreateReq;
import com.practice.req.ProjectSearchReq;
import com.practice.req.ProjectUpdateReq;

import java.util.List;

public interface ProjectService {
    ProjectDTO deleteProject(Integer id);

    ProjectDTO createProject(ProjectCreateReq projectCreateReq);

    ProjectDTO updateProject(Integer id, ProjectUpdateReq projectUpdateReq);

    PageDTO<ProjectDTO> getAllProjects(ProjectSearchReq projectSearchReq);

    List<ProjectDTO> getAllProjects();


}
