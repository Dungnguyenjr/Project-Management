package com.practice.req;

import com.practice.dto.ClassDTO;
import lombok.Data;

import java.util.List;
@Data
public class CourseUpdateReq {
    private int id;

    private String courseName;
    private int startYear;


}
