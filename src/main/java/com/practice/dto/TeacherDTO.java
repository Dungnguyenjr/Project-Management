package com.practice.dto;

import com.practice.enums.EnumGender;
import com.practice.enums.EnumRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
@Data
public class TeacherDTO {
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

    private String teacherCode;

    private long fieldId;

    private int courseId;
}
