package com.practice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table
public class Year {
    //+	STT, Năm, Số lượng đề tài, DS đề tài (Có thể xem ds đề tài tại đây), Ngày tạo

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer year;

    @Transient
    public int getNumberProject() {
        return project != null ? project.size() : 0;
    }


    @OneToMany(mappedBy = "year")
    @JsonIgnore
    private List<Project> project;

    @Column(name = "date_create",updatable = false)
    @CreationTimestamp

    private LocalDate dateCreate;



}
