package com.practice.req;

import lombok.Data;

@Data
public class ChangePasswordReq {
    private String oldPassword;
    private String newPassword;
}
