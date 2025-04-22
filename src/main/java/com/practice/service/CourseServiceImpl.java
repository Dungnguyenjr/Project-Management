package com.practice.service;

import com.practice.dto.CourseDTO;
import com.practice.entity.CourseEntity;
import com.practice.repository.CourseRepository;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
