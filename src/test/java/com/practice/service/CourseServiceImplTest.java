package com.practice.service;

import com.practice.dto.CourseDTO;
import com.practice.entity.CourseEntity;
import com.practice.repository.CourseRepository;
import com.practice.req.CourseCreatReq;
import com.practice.req.CourseUpdateReq;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    public void createCourseTest() {
        // Setup request
        CourseCreatReq req = new CourseCreatReq();
        req.setCourseName("Khoá CN23489");

        // Mock entity and DTO
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setCourseName(req.getCourseName());

        CourseDTO expectedCourseDTO = new CourseDTO();
        expectedCourseDTO.setCourseName(req.getCourseName());

        // Mock ModelMapper and repository behavior
        when(modelMapper.map(req, CourseEntity.class)).thenReturn(courseEntity);
        when(courseRepo.save(courseEntity)).thenReturn(courseEntity);
        when(modelMapper.map(courseEntity, CourseDTO.class)).thenReturn(expectedCourseDTO);

        // Act
        CourseDTO createdCourse = courseService.createCourse(req);

        // Assert
        assertNotNull(createdCourse);
        assertEquals("Khoá CN23489", createdCourse.getCourseName());
    }

    @Test
    public void updateCourseTest() {
        // Setup request
        CourseUpdateReq req = new CourseUpdateReq();
        req.setCourseName("Khoá CN23489");

        // Mock entity
        CourseEntity existingCourse = new CourseEntity();
        existingCourse.setCourseName("Khoá CN");

        CourseDTO expectedCourseDTO = new CourseDTO();
        expectedCourseDTO.setCourseName("Khoá CN23489");

        // Mock repository behavior
        when(courseRepo.findById(1)).thenReturn(java.util.Optional.of(existingCourse));
        when(courseRepo.save(existingCourse)).thenReturn(existingCourse);
        when(modelMapper.map(existingCourse, CourseDTO.class)).thenReturn(expectedCourseDTO);

        // Act
        CourseDTO updatedCourse = courseService.updateCourse(1, req);

        // Assert
        assertNotNull(updatedCourse);
        assertEquals("Khoá CN23489", updatedCourse.getCourseName());
    }

    @Test
    public void deleteCourseTest() {
        // Setup
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setCourseName("Khoá CN");
        when(courseRepo.findById(1)).thenReturn(java.util.Optional.of(courseEntity));
        courseService.deleteCourse(1);
        verify(courseRepo, times(1)).deleteById(1);  // Ensure deleteById is called
    }

    @Test
    public void deleteCourseNotFoundTest() {
        when(courseRepo.findById(1)).thenReturn(java.util.Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> courseService.deleteCourse(1));
    }
}
