package com.practice.controller;

import com.practice.entity.Account;
import com.practice.req.StudentCreateReq;
import com.practice.req.TeacherCreateReq;
import com.practice.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Student Controller")
@RestController
@RequestMapping("api/student")
public class StudentController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createUser(@RequestBody StudentCreateReq studentCreateReq,
                                              @RequestHeader("Authorization") String jwt) throws Exception {
        accountService.findAccountByJwtToken(jwt);
        Account savedAccount = accountService.saveAccount(studentCreateReq);
        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateStudent(@PathVariable int id,
                                                 @RequestBody StudentCreateReq studentCreateReq) {
        Account updateStudent = accountService.updateStudent(studentCreateReq, id);
        return new ResponseEntity<>(updateStudent, HttpStatus.OK);
    }

}

