package com.practice.controller;

import com.practice.dto.CourseDTO;
import com.practice.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Tag(name = "Course Controller")
@RestController
@RequestMapping("api/admin/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/search-course")
    public List<CourseDTO> searchCourse (@RequestParam(required = false) String courseName){
        return courseService.findAllByCourseName(courseName);
    }

}
