package com.practice.req;

import com.practice.entity.Project;
import lombok.Data;

import java.util.List;

@Data
public class YearCreateReq {

    private int id;

    private Integer year;

    private List<Integer> project;

}
