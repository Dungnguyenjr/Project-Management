package com.practice.service;

import com.practice.dto.CourseDTO;
import com.practice.entity.CourseEntity;
import com.practice.repository.CourseRepository;
import com.practice.req.CourseCreatReq;
import com.practice.req.CourseUpdateReq;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CourseRepository courseRepo;

    @Override
    public List<CourseDTO> findAllByCourseName(String courseName) {
        List<CourseEntity> courses;
        List<CourseDTO> courseDTOS = new ArrayList<>();
        if (StringUtils.isEmpty(courseName)){
            courses  = courseRepo.findAll();
        } else {
            courses  = courseRepo.findAllByCourseName(courseName);
        }
        for (CourseEntity entity : courses){
            CourseDTO dto = modelMapper.map(entity, CourseDTO.class);
            courseDTOS.add(dto);
        }
        return courseDTOS;

    }

    @Override
    public CourseDTO createCourse(CourseCreatReq courseCreatReq) {
        // Tạo mới khóa học
        CourseEntity courseEntity = modelMapper.map(courseCreatReq, CourseEntity.class);
        courseEntity = courseRepo.save(courseEntity);
        return modelMapper.map(courseEntity, CourseDTO.class);
    }

    @Override
    public CourseDTO updateCourse(int id, CourseUpdateReq courseUpdateReq) {
        // Cập nhật khóa học
        Optional<CourseEntity> courseEntityOpt = courseRepo.findById(id);
        if (courseEntityOpt.isPresent()) {
            CourseEntity courseEntity = courseEntityOpt.get();
            courseEntity.setCourseName(courseUpdateReq.getCourseName());
            courseEntity = courseRepo.save(courseEntity);
            return modelMapper.map(courseEntity, CourseDTO.class);
        }
        return null; // trả về null nếu không tìm thấy khóa học
    }

    @Override
    public void deleteCourse(int id) {
        // Xóa khóa học
        Optional<CourseEntity> courseEntityOpt = courseRepo.findById(id);
        if (courseEntityOpt.isPresent()) {
            courseRepo.deleteById(id);
        } else {
            throw new EntityNotFoundException("Khóa học không tồn tại");
        }
    }
}
