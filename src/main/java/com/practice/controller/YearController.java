package com.practice.controller;

import com.practice.dto.YearDTO;
import com.practice.req.YearCreateReq;
import com.practice.req.YearUpdateReq;
import com.practice.service.YearService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "years Controller")
@RestController
@RequestMapping("/api/years")
@CrossOrigin(origins = "*")
public class YearController {

    @Autowired
    private YearService yearService;

    @PostMapping
    public YearDTO createYear(@RequestBody YearCreateReq req) {
        return yearService.createYear(req);
    }

    @PutMapping("/{id}")
    public ResponseEntity<YearDTO> updateYear(@PathVariable Long id, @RequestBody YearUpdateReq req) {
        return ResponseEntity.ok(yearService.updateYear(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteYear(@PathVariable Long id) {
        yearService.deleteYear(id);
        return ResponseEntity.noContent().build();
    }

}
