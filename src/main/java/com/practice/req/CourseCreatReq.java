package com.practice.req;

import com.practice.dto.ClassDTO;
import lombok.Data;

import java.util.List;
@Data
public class CourseCreatReq {

    private int id;

    private String courseName;
    private List<ClassDTO> classEntities;
}
