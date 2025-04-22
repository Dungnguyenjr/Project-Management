package com.practice.controller;

import com.practice.dto.PageDTO;
import com.practice.dto.ProjectDTO;
import com.practice.req.ProjectCreateReq;
import com.practice.req.ProjectSearchReq;
import com.practice.service.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Project Controller")
@Slf4j
@RestController
@RequestMapping("api/project")
@CrossOrigin("*")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectCreateReq projectCreateReq) {
        try {
            ProjectDTO newProject = projectService.createProject(projectCreateReq);
            return new ResponseEntity<>(newProject, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            log.error("Error creating project: ", e);  // Log lỗi để kiểm tra
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Integer id, @RequestBody ProjectCreateReq projectCreateReq) {
        try {
            ProjectDTO updatedProject = projectService.updateProject(id, projectCreateReq);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error updating project with id {}: ", id, e);  // Log lỗi để kiểm tra
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer id) {
        try {
            projectService.deleteProject(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            log.error("Error deleting project with id {}: ", id, e);  // Log lỗi để kiểm tra
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<PageDTO<ProjectDTO>> searchProjects(@RequestBody ProjectSearchReq searchReq) {
        PageDTO<ProjectDTO> projects = projectService.getAllProjects(searchReq);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }



}
