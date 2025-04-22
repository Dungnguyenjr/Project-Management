package com.practice.controller;

import com.practice.entity.Account;
import com.practice.req.TeacherCreateReq;
import com.practice.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Teacher Controller")
@RestController
@RequestMapping("/api/admin/teacher")
public class TeacherController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createTeacher(@RequestBody TeacherCreateReq teacherCreateReq,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        accountService.findAccountByJwtToken(jwt);
        Account savedAccount = accountService.createTeacher(teacherCreateReq);
        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateTeacher(@PathVariable int id,
                                                 @RequestBody TeacherCreateReq teacherCreateReq) {
        Account updatedAccount = accountService.updateTeacher(teacherCreateReq, id);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

}
