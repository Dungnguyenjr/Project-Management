package com.practice.service;

import com.practice.dto.FieldDTO;
import com.practice.entity.Field;
import com.practice.repository.FieldRepository;
import com.practice.req.FieldCreateReq;
import com.practice.req.FieldUpdateReq;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FieldServiceImpl implements FieldService {

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<FieldDTO> getAllFields() {
        return fieldRepository.findAll()
                .stream()
                .map(field -> modelMapper.map(field, FieldDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public FieldDTO getFieldById(Long id) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Field not found with id: " + id));
        return modelMapper.map(field, FieldDTO.class);
    }

    // Thêm ngành nghề
    @Override
    public FieldDTO createField(FieldCreateReq fieldCreateReq) {
        Field field = modelMapper.map(fieldCreateReq, Field.class);
        return modelMapper.map(fieldRepository.save(field), FieldDTO.class);
    }

    // Sửa ngành nghề
    @Override
    public FieldDTO updateField(Long id, FieldUpdateReq fieldUpdateReq) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Field not found with id: " + id));
        field.setName(fieldUpdateReq.getName());
        field.setCode(fieldUpdateReq.getCode());
        return modelMapper.map(fieldRepository.save(field), FieldDTO.class);
    }

    // Xóa ngành nghề
    @Override
    public FieldDTO deleteField(Long id) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Field not found with id: " + id));
        fieldRepository.delete(field);
        return modelMapper.map(field, FieldDTO.class);
    }
}
