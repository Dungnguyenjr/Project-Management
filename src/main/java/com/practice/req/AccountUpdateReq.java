package com.practice.req;

import lombok.Data;

import java.util.Date;

@Data
public class AccountUpdateReq {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private String role;
    private String avatar;
    private boolean isActive;
    private String uuid;
    private Date birthday;
    private String note;
    private String studyAndWork;
}
