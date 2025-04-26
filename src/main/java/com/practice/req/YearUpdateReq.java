package com.practice.req;

import lombok.Data;

import java.util.List;

@Data
public class YearUpdateReq {

    private int id;

    private Integer year;

    private List<Integer> project;

}
