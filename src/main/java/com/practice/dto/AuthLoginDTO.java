package com.practice.dto;

import com.practice.enums.EnumRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginDTO {

    private String message;

    private String jwt;

    private EnumRole role;

}
