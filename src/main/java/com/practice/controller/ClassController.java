package com.practice.controller;

import com.practice.dto.ClassDTO;
import com.practice.req.ClassCreateReq;
import com.practice.req.ClassUpdateReq;
import com.practice.service.ClassService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Class Controller")
@Slf4j
@RestController
@RequestMapping("api/admin/class")
public class ClassController {

    @Autowired
    private ClassService classService;


    @GetMapping("/search-name")
    public List<ClassDTO> searchClassName(@RequestParam(required = false) String className) {
        return classService.findByClassName(className);
    }


    @GetMapping("/all")
    public List<ClassDTO> getAllClass() {
        log.info("Get all class");
        return classService.getAllClass();
    }


    @GetMapping("/page")
    public Page<ClassDTO> getPage(@RequestParam(required = false, defaultValue = "1") int page,
                                  @RequestParam(required = false, defaultValue = "10") int size) {
        if (size <= 0) {
            size = 10;
        }
        if (page > 0) {
            page -= 1;
        }
        return classService.getPage(page, size);
    }


    @PostMapping
    public ResponseEntity<?> createClass(@RequestBody ClassCreateReq classCreateReq) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bạn không có quyền thực hiện hành động này");
        }

        ClassDTO createdClass = classService.createClass(classCreateReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClass);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClass(@PathVariable int id, @RequestBody ClassUpdateReq classUpdateReq) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bạn không có quyền thực hiện hành động này");
        }

        ClassDTO classDTO = classService.updateClass(id, classUpdateReq);
        if (classDTO == null) {
            return new ResponseEntity<>("Không tìm thấy id: " + id, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(classDTO);
    }

    // Phương thức xóa lớp chỉ cho phép ADMIN
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClass(@PathVariable int id) {
        // Kiểm tra quyền ADMIN
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bạn không có quyền thực hiện hành động này");
        }

        try {
            classService.deleteClass(id);
            return new ResponseEntity<>("Xóa thành công lớp với id: " + id, HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>("Không tìm thấy id: " + id, HttpStatus.BAD_REQUEST);
        }
    }

}
