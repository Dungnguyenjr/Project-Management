package com.practice.dto;

import com.practice.enums.EnumRoleMember;
import lombok.Data;

@Data
public class StudentGroupDTO {
    private long id;
    private String fullName;
    private EnumRoleMember enumRoleMember;
}
