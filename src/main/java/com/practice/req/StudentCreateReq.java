package com.practice.req;

import com.practice.enums.EnumGender;
import com.practice.enums.EnumRole;
import lombok.Data;

import java.util.Date;

@Data
public class StudentCreateReq {

    private String username;

    private String password;

    private String fullName;

    private String email;

    private String phone;

    private EnumRole role;


    private String avatar;

    private String uuid;

    private Date birthday;

    private EnumGender gender;

    private String address;

    private String note;

    private String studentCode;

    private long fieldId;

    private int classEntityId;

    private long batchEntityId;
}
