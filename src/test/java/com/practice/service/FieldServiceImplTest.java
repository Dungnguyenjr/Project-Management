package com.practice.service;

import com.practice.dto.FieldDTO;
import com.practice.entity.Field;
import com.practice.repository.FieldRepository;
import com.practice.req.FieldCreateReq;
import com.practice.req.FieldUpdateReq;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FieldServiceImplTest {

    @Mock
    private FieldRepository fieldRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private FieldServiceImpl fieldService;

    @Test
    public void createFieldTest() {
        // Setup request
        FieldCreateReq req = new FieldCreateReq();
        req.setCode("Mã 123");
        req.setName("Ngành CNTT");

        // Mock the entity and DTO conversion
        Field field = new Field();
        field.setCode(req.getCode());
        field.setName(req.getName());

        FieldDTO expectedFieldDTO = new FieldDTO();
        expectedFieldDTO.setCode(req.getCode());
        expectedFieldDTO.setName(req.getName());

        // Mock ModelMapper behavior
        when(modelMapper.map(req, Field.class)).thenReturn(field);
        when(fieldRepo.save(Mockito.any(Field.class))).thenReturn(field);
        when(modelMapper.map(field, FieldDTO.class)).thenReturn(expectedFieldDTO);

        // Act
        FieldDTO createdField = fieldService.createField(req);

        // Assert
        assertNotNull(createdField);
        assertEquals("Mã 123", createdField.getCode());
        assertEquals("Ngành CNTT", createdField.getName());
    }

    // Test sửa ngành nghề (update)
    @Test
    public void updateFieldTest() {
        // Setup
        Long fieldId = 1L;
        FieldUpdateReq updateReq = new FieldUpdateReq();
        updateReq.setCode("Mã 124");
        updateReq.setName("Ngành Khoa học Máy tính");

        Field field = new Field();
        field.setId(fieldId);
        field.setCode("Mã 123");
        field.setName("Ngành CNTT");

        Field updatedField = new Field();
        updatedField.setId(fieldId);
        updatedField.setCode(updateReq.getCode());
        updatedField.setName(updateReq.getName());

        FieldDTO updatedFieldDTO = new FieldDTO();
        updatedFieldDTO.setCode(updateReq.getCode());
        updatedFieldDTO.setName(updateReq.getName());

        // Mock behavior
        when(fieldRepo.findById(fieldId)).thenReturn(java.util.Optional.of(field));
        when(fieldRepo.save(Mockito.any(Field.class))).thenReturn(updatedField);
        when(modelMapper.map(updatedField, FieldDTO.class)).thenReturn(updatedFieldDTO);

        // Act
        FieldDTO result = fieldService.updateField(fieldId, updateReq);

        // Assert
        assertNotNull(result);
        assertEquals("Mã 124", result.getCode());
        assertEquals("Ngành Khoa học Máy tính", result.getName());
    }

    // Test xóa ngành nghề (delete)
    @Test
    public void deleteFieldTest() {
        // Setup
        Long fieldId = 1L;
        Field field = new Field();
        field.setId(fieldId);
        field.setCode("Mã 123");
        field.setName("Ngành CNTT");

        FieldDTO fieldDTO = new FieldDTO();
        fieldDTO.setCode(field.getCode());
        fieldDTO.setName(field.getName());

        // Mock behavior
        when(fieldRepo.findById(fieldId)).thenReturn(java.util.Optional.of(field));
        doNothing().when(fieldRepo).delete(field);
        when(modelMapper.map(field, FieldDTO.class)).thenReturn(fieldDTO);

        // Act
        FieldDTO deletedField = fieldService.deleteField(fieldId);

        // Assert
        assertNotNull(deletedField);
        assertEquals("Mã 123", deletedField.getCode());
        assertEquals("Ngành CNTT", deletedField.getName());
    }

    // Test tìm ngành nghề theo ID (get by ID)
    @Test
    public void getFieldByIdTest() {
        // Setup
        Long fieldId = 1L;
        Field field = new Field();
        field.setId(fieldId);
        field.setCode("Mã 123");
        field.setName("Ngành CNTT");

        FieldDTO fieldDTO = new FieldDTO();
        fieldDTO.setCode(field.getCode());
        fieldDTO.setName(field.getName());

        // Mock behavior
        when(fieldRepo.findById(fieldId)).thenReturn(java.util.Optional.of(field));
        when(modelMapper.map(field, FieldDTO.class)).thenReturn(fieldDTO);

        // Act
        FieldDTO result = fieldService.getFieldById(fieldId);

        // Assert
        assertNotNull(result);
        assertEquals("Mã 123", result.getCode());
        assertEquals("Ngành CNTT", result.getName());
    }

    // Test không tìm thấy ngành nghề khi cố gắng xóa (Field not found for delete)
    @Test
    public void deleteFieldNotFoundTest() {
        // Setup
        Long fieldId = 1L;

        // Mock behavior
        when(fieldRepo.findById(fieldId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            fieldService.deleteField(fieldId);
        });
        assertEquals("Field not found with id: " + fieldId, exception.getMessage());
    }
}
