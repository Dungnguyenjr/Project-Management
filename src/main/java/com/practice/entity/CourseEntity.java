package com.practice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "course_entity")
public class CourseEntity {

    //+	STT, (*)Tên khóa, năm bắt đầu

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String courseName;

    @Column(name = "start_year")
    private int startYear;

    @OneToMany(mappedBy = "courseEntity")
    @JsonManagedReference
    List<ClassEntity> classEntities = new ArrayList<>();
}
