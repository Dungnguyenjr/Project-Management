package com.practice.service;

import com.practice.dto.ClassDTO;
import com.practice.entity.ClassEntity;
import com.practice.repository.ClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ClassServiceImplTest {

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
}