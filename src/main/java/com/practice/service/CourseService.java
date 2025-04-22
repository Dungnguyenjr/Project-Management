package com.practice.service;

import com.practice.dto.CourseDTO;

import java.util.List;

public interface CourseService {

    List<CourseDTO> findAllByCourseName(String courseName);
}
