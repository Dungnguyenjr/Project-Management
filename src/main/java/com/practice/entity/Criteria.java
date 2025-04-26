package com.practice.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private Project project;
}
