package com.practice.dto;

import com.practice.enums.EnumGender;
import com.practice.enums.EnumRole;
import com.practice.enums.EnumRoleMember;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class StudentDTO {
    private int id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String fullName;

    @NotNull
    @Email
    private String email;

    private String phone;


    @NotNull
    private EnumRole role;


    private String avatar;

    private Date birthday;

    @NotNull
    private EnumGender gender;

    private String address;

    private String note;

    private String uuid;

    private String studentCode;

    private long fieldId;

    private int classEntityId;

    private long batchEntityId;

}
