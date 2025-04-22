package com.practice.controller;

import com.practice.dto.FieldDTO;
import com.practice.service.FieldService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Field Controller")
@RestController
@RequestMapping("api/field")
@CrossOrigin(value = "*")
public class FieldController {

    @Autowired
    private FieldService fieldService;

    // Thêm ngành nghề
    @PostMapping("")
    public FieldDTO createField(@RequestBody FieldDTO fieldDTO) {
        return fieldService.createField(fieldDTO);
    }

    // Sửa ngành nghề
    @PutMapping("/{id}")
    public FieldDTO updateField(@PathVariable Long id, @RequestBody FieldDTO fieldDTO) {
        return fieldService.updateField(id, fieldDTO);
    }

    // xoa nganh nghe
    @DeleteMapping("/{id}")
    public  FieldDTO deleteField(@PathVariable Long id){
        return  fieldService.deleteField(id);
    }

}
