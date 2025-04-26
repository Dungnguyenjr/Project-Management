package com.practice.controller;

import com.practice.req.BatchCreatReq;
import com.practice.entity.BatchEntity;
import com.practice.req.BatchUpdateReq;
import com.practice.service.BatchService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Tag(name = "Batch Controller")
@Validated
@RestController
@RequestMapping("api/batch")
public class BatchController {
    final BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @PostMapping("create")
    public ResponseEntity<?> createBatch(@Valid @RequestBody BatchCreatReq request) {
        try {
            BatchEntity nameAlready = batchService.getBatchByName(request.getName());
            if (nameAlready != null) {
                return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", "Tên đợt đã tồn tại! Vui lòng nhập lại tên khác."));
            }
            BatchEntity yearAlready = batchService.getBatchByYear(request.getYear());
            if (yearAlready != null) {
                return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", "Năm đã tồn tại! Vui lòng nhập lại năm khác."));
            }
            BatchEntity response = batchService.createBatch(request);
            return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> updateBatch(@Valid @RequestParam("id") Long id, @Valid @RequestBody BatchUpdateReq batchUpdateReq) {
        try {
            BatchEntity idCheck = batchService.idCheck(id);
            if (idCheck != null) {
                BatchEntity response = batchService.updateBatch(id, batchUpdateReq);
                return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", response));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", "Đợt không tồn tại!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBatch(@PathVariable Long id) {
        try {
            BatchEntity idCheck = batchService.idCheck(id);
            if (idCheck != null) {
                batchService.deleteBatch(id);
                return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", "Xóa đợt thành công"));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", "Đợt không tồn tại!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", e.getMessage()));
        }
    }

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
