package com.practice.controller;

import com.practice.dto.ClassDTO;
import com.practice.service.ClassService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Search Class Name Controller")
@RestController
@RequestMapping("/api")
@CrossOrigin(value = "*")
public class SearchClassNameController {

    @Autowired
    private ClassService classService;

    @GetMapping("/search-class-name")
    public List<ClassDTO> searchClassName (@RequestParam(required = false) String className){
        return classService.findByClassName(className);
    }
}
