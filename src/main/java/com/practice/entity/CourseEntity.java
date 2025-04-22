package com.practice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "course_entity")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String courseName;

    @OneToMany(mappedBy = "courseEntity")
    List<ClassEntity> classEntities = new ArrayList<>();
}
