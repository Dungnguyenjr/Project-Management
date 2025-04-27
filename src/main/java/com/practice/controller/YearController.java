package com.practice.controller;

import com.practice.dto.YearDTO;
import com.practice.req.YearCreateReq;
import com.practice.req.YearUpdateReq;
import com.practice.service.YearService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Years Controller")
@RestController
@RequestMapping("/api/years")
@CrossOrigin(origins = "*")
public class YearController {

    @Autowired
    private YearService yearService;

    private boolean hasRoleTeacher() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_TEACHER"));
    }

    @PostMapping
    public ResponseEntity<?> createYear(@RequestBody YearCreateReq req) {
        if (!hasRoleTeacher()) {
            return ResponseEntity.status(403).body("Bạn không có quyền thực hiện hành động này");
        }
        return ResponseEntity.ok(yearService.createYear(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateYear(@PathVariable Long id, @RequestBody YearUpdateReq req) {
        if (!hasRoleTeacher()) {
            return ResponseEntity.status(403).body("Bạn không có quyền thực hiện hành động này");
        }
        return ResponseEntity.ok(yearService.updateYear(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteYear(@PathVariable Long id) {
        if (!hasRoleTeacher()) {
            return ResponseEntity.status(403).body("Bạn không có quyền thực hiện hành động này");
        }
        yearService.deleteYear(id);
        return ResponseEntity.noContent().build();
    }
}
