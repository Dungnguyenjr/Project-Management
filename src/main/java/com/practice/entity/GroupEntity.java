package com.practice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table
public class Group {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany
    private BatchEntity batchEntity ( trong bảng có tên là name lấy tên )

    private String GroupName;

    @OneToMany
    private Project project ( trong có private String projectName; lấy projectName)

        cái nyà là tí nữa làm bảng lấy danh sách sv xong nơi này chưa số lượng sinh viên
    private Project project ( trong có private String projectName; lấy projectName)


}
