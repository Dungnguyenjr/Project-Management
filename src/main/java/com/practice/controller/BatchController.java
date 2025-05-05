package com.practice.controller;

import com.practice.repository.BatchRepository;
import com.practice.req.BatchCreatReq;
import com.practice.entity.BatchEntity;
import com.practice.req.BatchUpdateReq;
import com.practice.service.BatchService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Tag(name = "Batch Controller")
@Validated
@RestController
@RequestMapping("api/batch")
public class BatchController {

    final BatchService batchService;
    @Autowired
    private BatchRepository batchRepo;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }


    @PostMapping("create")
    public ResponseEntity<?> createBatch(@Valid @RequestBody BatchCreatReq request) {
        try {
            // Lấy thông tin người dùng
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Collections.singletonMap("message", "Bạn không có quyền thực hiện hành động này"));
            }

            BatchEntity existingBatch = batchRepo.findByNameAndYear(request.getName(), request.getYear());
            if (existingBatch != null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(Collections.singletonMap("message", "Tên đợt đã tồn tại cho năm này! Vui lòng nhập lại."));
            }

            // Tạo đợt mới
            BatchEntity response = batchService.createBatch(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    // Phương thức cập nhật batch chỉ cho phép admin
    @PutMapping("update")
    public ResponseEntity<?> updateBatch(@Valid @RequestParam("id") Long id, @Valid @RequestBody BatchUpdateReq batchUpdateReq) {
        try {
            // Kiểm tra vai trò của người dùng (admin)
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Collections.singletonMap("message", "Bạn không có quyền thực hiện hành động này"));
            }

            BatchEntity idCheck = batchService.idCheck(id);
            if (idCheck != null) {
                BatchEntity response = batchService.updateBatch(id, batchUpdateReq);
                return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", response));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "Đợt không tồn tại!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    // Phương thức xóa batch chỉ cho phép admin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBatch(@PathVariable Long id) {
        try {
            // Kiểm tra vai trò của người dùng (admin)
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Collections.singletonMap("message", "Bạn không có quyền thực hiện hành động này"));
            }

            BatchEntity idCheck = batchService.idCheck(id);
            if (idCheck != null) {
                batchService.deleteBatch(id);
                return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", "Xóa đợt thành công"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "Đợt không tồn tại!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    // Phương thức tìm kiếm batch theo tên
    @GetMapping("search-name")
    public ResponseEntity<?> searchNameBatch(@RequestParam(required = false) String nameBatch) {
        try {
            List<BatchEntity> response = batchService.searchNameBatch(nameBatch);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", e.getMessage()));
        }
    }
}
