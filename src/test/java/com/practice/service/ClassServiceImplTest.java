package com.practice.service;

import com.practice.dto.ClassDTO;
import com.practice.entity.ClassEntity;
import com.practice.repository.ClassRepository;
import com.practice.req.ClassCreateReq;
import com.practice.req.ClassUpdateReq;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class ClassServiceImplTest {

    @Mock
    private ClassRepository classRepo;
    @InjectMocks
    private ClassServiceImpl classService;
    @Mock
    private ModelMapper mapper;

    private ClassDTO classDTO;
    private ClassEntity classEntityResult;
    private List<ClassEntity> classEntityList;

    @BeforeEach
    void setUp() {
        classDTO = new ClassDTO();
        classDTO.setClassName("TH25.11");
        classDTO.setCourse("k25");

        classEntityResult = new ClassEntity();
        classEntityResult.setId(10);
        classEntityResult.setClassName("TH25.11");
        classEntityResult.setCourse("k25");

        classEntityList = new ArrayList<>();
        classEntityList.add(classEntityResult);
    }

    @Test
    void findByClassName() {

        when(classRepo.findByClassName(anyString())).thenReturn(classEntityList);
        when(mapper.map(any(), any())).thenReturn(classDTO);
        List<ClassDTO> classDTOList = classService.findByClassName("TH25.11");
        assertEquals("TH25.11", classDTOList.get(0).getClassName());
        assertEquals("k25", classDTOList.get(0).getCourse());
    }

    @Test
    void createClass() {
        ClassCreateReq classCreateReq = new ClassCreateReq();
        classCreateReq.setClassName("TH26.12");
        classCreateReq.setCourse("k26");

        ClassEntity savedEntity = new ClassEntity();
        savedEntity.setClassName("TH26.12");
        savedEntity.setCourse("k26");

        ClassDTO expectedDTO = new ClassDTO();
        expectedDTO.setClassName("TH26.12");
        expectedDTO.setCourse("k26");

        when(classRepo.save(any(ClassEntity.class))).thenReturn(savedEntity);
        when(mapper.map(any(ClassEntity.class), eq(ClassDTO.class))).thenReturn(expectedDTO);

        ClassDTO createdClass = classService.createClass(classCreateReq);

        assertEquals("TH26.12", createdClass.getClassName());
        assertEquals("k26", createdClass.getCourse());
    }


    @Test
    void updateClass() {
        ClassUpdateReq classUpdateReq = new ClassUpdateReq();
        classUpdateReq.setClassName("TH26.12");
        classUpdateReq.setCourse("k26");

        // Mock dữ liệu
        when(classRepo.findById(anyInt())).thenReturn(java.util.Optional.of(classEntityResult));
        when(classRepo.save(any(ClassEntity.class))).thenReturn(classEntityResult);
        when(mapper.map(any(), any())).thenReturn(classDTO);

        // Gọi service và kiểm tra kết quả
        ClassDTO updatedClass = classService.updateClass(10, classUpdateReq);

        assertEquals("TH26.12", updatedClass.getClassName());
        assertEquals("k25", updatedClass.getCourse());  // Kiểm tra dữ liệu cập nhật.
    }

    @Test
    void updateClassNotFound() {
        ClassUpdateReq classUpdateReq = new ClassUpdateReq();
        classUpdateReq.setClassName("TH26.12");
        classUpdateReq.setCourse("k26");

        // Mock khi không tìm thấy lớp
        when(classRepo.findById(anyInt())).thenReturn(java.util.Optional.empty());

        // Gọi service và kiểm tra kết quả
        ClassDTO updatedClass = classService.updateClass(10, classUpdateReq);

        assertNull(updatedClass);  // Lớp không tồn tại, trả về null
    }

    @Test
    void deleteClass() {
        when(classRepo.findById(anyInt())).thenReturn(java.util.Optional.of(classEntityResult));
        classService.deleteClass(10);
        verify(classRepo, times(1)).delete(any(ClassEntity.class));
    }

    @Test
    void deleteClassNotFound() {
        // Mock không tìm thấy lớp
        when(classRepo.findById(anyInt())).thenReturn(java.util.Optional.empty());

        // Gọi service và kiểm tra exception
        try {
            classService.deleteClass(10);
        } catch (EntityNotFoundException e) {
            assertEquals("Không tìm thấy lớp với id: 10", e.getMessage());
        }
    }


    @Test
    void getAllClass() {
        // Mock dữ liệu
        when(classRepo.findAll()).thenReturn(classEntityList);
        when(mapper.map(any(), any())).thenReturn(classDTO);

        // Gọi service và kiểm tra kết quả
        List<ClassDTO> allClasses = classService.getAllClass();

        assertEquals(1, allClasses.size());  // Kiểm tra số lớp
        assertEquals("TH25.11", allClasses.get(0).getClassName());  // Kiểm tra tên lớp
    }
    @Test
    void getPage() {
        int page = 0, size = 1;
        PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<ClassEntity> mockPage = new PageImpl<>(classEntityList, pageable, classEntityList.size());
        when(classRepo.findAll(pageable)).thenReturn(mockPage);
        when(mapper.map(any(ClassEntity.class), eq(ClassDTO.class))).thenReturn(classDTO);

        Page<ClassDTO> result = classService.getPage(page, size);

        assertEquals(1, result.getTotalElements());
        assertEquals("TH25.11", result.getContent().get(0).getClassName());
    }




}