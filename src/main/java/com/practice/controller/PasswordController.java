package com.practice.controller;

import com.practice.req.ChangePasswordReq;
import com.practice.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Password Controller")
@RequestMapping("/api/account")
public class PasswordController {
    @Autowired
    private AccountService accountService;

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestHeader("Authorization") String jwt,
                                                 @RequestBody ChangePasswordReq changePasswordReq) {
        try {
            accountService.changePassword(jwt, changePasswordReq);
            return ResponseEntity.ok("Đổi mật khẩu thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
