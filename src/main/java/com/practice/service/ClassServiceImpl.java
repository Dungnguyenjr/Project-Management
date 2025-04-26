package com.practice.service;

import com.practice.dto.ClassDTO;
import com.practice.entity.ClassEntity;
import com.practice.repository.ClassRepository;
import com.practice.req.ClassCreateReq;
import com.practice.req.ClassUpdateReq;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClassRepository classRepo;

    @Override
    public List<ClassDTO> findByClassName(String className) {
        List<ClassEntity> classNames;
        List<ClassDTO> classDTOS = new ArrayList<>();
        if (StringUtils.isEmpty(className)){
            classNames  = classRepo.findAll();
        } else {
            classNames  = classRepo.findByClassName(className);
        }
        for (ClassEntity entity : classNames){
            ClassDTO dto = modelMapper.map(entity, ClassDTO.class);
            classDTOS.add(dto);
        }
        return classDTOS;

    }

    @Override
    public List<ClassDTO> getAllClass() {
        return classRepo.findAll()
                .stream()
                .map(classEntity -> modelMapper.map(classEntity, ClassDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ClassDTO> getPage( int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<ClassEntity> pages = classRepo.findAll(pageable);
        List<ClassDTO> dtoList = new ArrayList<>();
        for (ClassEntity entity : pages.getContent()) {
            ClassDTO dto = modelMapper.map(entity, ClassDTO.class);
            dtoList.add(dto);
        }
        return new PageImpl<>(dtoList, pageable, pages.getTotalElements());
    }

    @Override
    public ClassDTO createClass(ClassCreateReq classCreateReq) {
        ClassEntity classEntity = new ClassEntity();
        classEntity.setClassName(classCreateReq.getClassName());
        classEntity.setCourse(classCreateReq.getCourse());
        return modelMapper.map(classRepo.save(classEntity), ClassDTO.class);
    }

    @Override
    public ClassDTO updateClass(int id, ClassUpdateReq classUpdateReq){
        ClassEntity classEntity = classRepo.findById(id)
                .orElse(null);
        if (classEntity == null) {
            return null;
        }
        classEntity.setClassName(classUpdateReq.getClassName());
        classEntity.setCourse(classUpdateReq.getCourse());
        return modelMapper.map(classRepo.save(classEntity), ClassDTO.class);
    }

    @Override
    public void deleteClass(int id) {
        ClassEntity classEntity = classRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy lớp với id: " + id));
        classRepo.delete(classEntity);
    }
}
