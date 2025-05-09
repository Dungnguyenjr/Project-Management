package com.practice.controller;

import com.practice.dto.CourseDTO;
import com.practice.req.CourseCreatReq;
import com.practice.req.CourseUpdateReq;
import com.practice.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Course Controller")
@RestController
@RequestMapping("api/admin/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // Phương thức tìm kiếm khóa học theo tên (không yêu cầu quyền admin)
    @GetMapping("/search-course")
    public List<CourseDTO> searchCourse(@RequestParam(required = false) String courseName) {
        return courseService.findAllByCourseName(courseName);
    }

    // Phương thức tạo khóa học mới chỉ cho phép ADMIN
    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody CourseCreatReq courseCreatReq) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bạn không có quyền thực hiện hành động này");
        }

        CourseDTO createdCourse = courseService.createCourse(courseCreatReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    // Phương thức cập nhật khóa học chỉ cho phép ADMIN
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable int id, @RequestBody CourseUpdateReq courseUpdateReq) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bạn không có quyền thực hiện hành động này");
        }

        CourseDTO updatedCourse = courseService.updateCourse(id, courseUpdateReq);
        if (updatedCourse == null) {
            return new ResponseEntity<>("Không tìm thấy id: " + id, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(updatedCourse);
    }

    // Phương thức xóa khóa học chỉ cho phép ADMIN
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable int id) {
        // Kiểm tra quyền ADMIN
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bạn không có quyền thực hiện hành động này");
        }

        try {
            courseService.deleteCourse(id);
            return new ResponseEntity<>("Xóa thành công khóa học với id: " + id, HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>("Không tìm thấy id: " + id, HttpStatus.BAD_REQUEST);
        }
    }
}
