package com.practice.service;

import com.practice.dto.FieldDTO;

import java.util.List;

public interface FieldService {
    //thêm ngành nghề
    FieldDTO createField(FieldDTO fieldDTO);

    //sửa ngành nghề
    FieldDTO updateField(Long id, FieldDTO fieldDTO);

    // xoa nganh nghe
    FieldDTO deleteField(Long id);
    List<FieldDTO> getAllFields();

    FieldDTO getFieldById(Long id);

}

