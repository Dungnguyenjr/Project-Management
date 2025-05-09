package com.practice.controller;

import com.practice.entity.Account;
import com.practice.enums.EnumRole;
import com.practice.req.AccountCreateReq;
import com.practice.req.AccountUpdateReq;
import com.practice.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Account Controller")
@RestController
@RequestMapping("api/admin")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping()
    public ResponseEntity<Account> createUser(@RequestBody AccountCreateReq accountCreateReq,
                                              @RequestHeader("Authorization") String jwt) throws Exception {
        accountService.findAccountByJwtToken(jwt);
        Account createAccount = accountService.createAccount(accountCreateReq);
        return new ResponseEntity<>(createAccount, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateUser(@PathVariable int id, @RequestBody AccountUpdateReq accountUpdateReq ,
                                              @RequestHeader("Authorization") String jwt) throws Exception {
        accountService.findAccountByJwtToken(jwt);
        Account account = accountService.updateAccount(accountUpdateReq,id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAccount/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id ,
                                        @RequestHeader("Authorization") String jwt )throws Exception{
        accountService.findAccountByJwtToken(jwt);
        accountService.deleteAccount(id);
        return new ResponseEntity<>("Xoa thanh cong",HttpStatus.OK);
    }

    // Lấy danh sách STUDENT
    @GetMapping("/students")
    public ResponseEntity<List<Account>> getAllStudents() {
        return ResponseEntity.ok(accountService.getAccountsByRole(EnumRole.STUDENT));
    }

    // Lấy danh sách TEACHER
    @GetMapping("/teachers")
    public ResponseEntity<List<Account>> getAllTeachers() {
        return ResponseEntity.ok(accountService.getAccountsByRole(EnumRole.TEACHER));
    }

    // Lấy danh sách ADMIN
    @GetMapping("/admins")
    public ResponseEntity<List<Account>> getAllAdmins() {
        return ResponseEntity.ok(accountService.getAccountsByRole(EnumRole.ADMIN));
    }

    @GetMapping("/me")
    public ResponseEntity<Account> getMyAccount(@RequestHeader("Authorization") String jwt) throws Exception {
        Account account = accountService.findAccountByJwtToken(jwt);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }



}
