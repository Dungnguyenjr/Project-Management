package com.practice.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Criteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String criteriaName;

    @ManyToOne
    @JoinColumn(name= "project_id")
    private Project project;
}
