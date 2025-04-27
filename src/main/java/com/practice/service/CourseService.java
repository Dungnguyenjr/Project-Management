package com.practice.service;

import com.practice.dto.CourseDTO;
import com.practice.req.CourseCreatReq;
import com.practice.req.CourseUpdateReq;

import java.util.List;

public interface CourseService {

    List<CourseDTO> findAllByCourseName(String courseName);
    // Tạo khóa học mới
    CourseDTO createCourse(CourseCreatReq courseCreatReq);

    // Cập nhật khóa học
    CourseDTO updateCourse(int id, CourseUpdateReq courseUpdateReq);

    // Xóa khóa học
    void deleteCourse(int id);

}
