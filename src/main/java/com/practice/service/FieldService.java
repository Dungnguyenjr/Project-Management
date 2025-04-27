package com.practice.service;

import com.practice.dto.FieldDTO;
import com.practice.req.FieldCreateReq;
import com.practice.req.FieldUpdateReq;

import java.util.List;

public interface FieldService {
    //thêm ngành nghề
    FieldDTO createField(FieldCreateReq fieldCreateReq);

    //sửa ngành nghề
    FieldDTO updateField(Long id, FieldUpdateReq fieldUpdateReq);

    // xoa nganh nghe
    FieldDTO deleteField(Long id);
    List<FieldDTO> getAllFields();

    FieldDTO getFieldById(Long id);

}

