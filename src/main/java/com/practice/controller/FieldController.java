package com.practice.controller;

import com.practice.dto.FieldDTO;
import com.practice.req.FieldCreateReq;
import com.practice.req.FieldUpdateReq;
import com.practice.service.FieldService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Tag(name = "Admin - Field Controller")
@RestController
@RequestMapping("api/admin/field")
@CrossOrigin(value = "*")
public class FieldController {

    @Autowired
    private FieldService fieldService;

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }

    // Thêm ngành nghề
    @PostMapping("")
    public ResponseEntity<?> createField(@RequestBody FieldCreateReq fieldCreateReq) {
        if (!isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("message", "Bạn không có quyền thực hiện hành động này"));
        }
        return ResponseEntity.ok(fieldService.createField(fieldCreateReq));
    }

    // Sửa ngành nghề
    @PutMapping("/{id}")
    public ResponseEntity<?> updateField(@PathVariable Long id, @RequestBody FieldUpdateReq fieldUpdateReq) {
        if (!isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("message", "Bạn không có quyền thực hiện hành động này"));
        }
        return ResponseEntity.ok(fieldService.updateField(id, fieldUpdateReq));
    }

    // Xóa ngành nghề
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteField(@PathVariable Long id) {
        if (!isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("message", "Bạn không có quyền thực hiện hành động này"));
        }
        return ResponseEntity.ok(fieldService.deleteField(id));
    }

    // Lấy tất cả ngành nghề
    @GetMapping("/all")
    public List<FieldDTO> getAllFields() {
        return fieldService.getAllFields();
    }

    // Lấy chi tiết ngành nghề theo ID
    @GetMapping("/{id}")
    public FieldDTO getFieldById(@PathVariable Long id) {
        return fieldService.getFieldById(id);
    }
}
