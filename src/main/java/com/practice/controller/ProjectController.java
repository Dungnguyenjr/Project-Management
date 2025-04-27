package com.practice.controller;

import com.practice.dto.PageDTO;
import com.practice.dto.ProjectDTO;
import com.practice.req.ProjectCreateReq;
import com.practice.req.ProjectSearchReq;
import com.practice.req.ProjectUpdateReq;
import com.practice.service.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Project Controller")
@Slf4j
@RestController
@RequestMapping("api/project")
@CrossOrigin("*")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    private boolean hasRoleTeacher() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_TEACHER"));
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectCreateReq projectCreateReq) {
        if (!hasRoleTeacher()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền thực hiện hành động này");
        }
        try {
            ProjectDTO newProject = projectService.createProject(projectCreateReq);
            return new ResponseEntity<>(newProject, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            log.error("Error creating project: ", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Integer id, @RequestBody ProjectUpdateReq projectUpdateReq) {
        if (!hasRoleTeacher()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền thực hiện hành động này");
        }
        try {
            ProjectDTO updatedProject = projectService.updateProject(id, projectUpdateReq);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error updating project with id {}: ", id, e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Integer id) {
        if (!hasRoleTeacher()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền thực hiện hành động này");
        }
        try {
            projectService.deleteProject(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            log.error("Error deleting project with id {}: ", id, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<PageDTO<ProjectDTO>> searchProjects(@RequestBody ProjectSearchReq searchReq) {
        PageDTO<ProjectDTO> projects = projectService.getAllProjects(searchReq);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }
}
