package com.practice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.practice.enums.EnumProjectStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
public class Project {
    //    STT, Tên đề tài, Mô tả, Trạng thái (Nháp, xét duyệt, Áp dụng), ngày tạo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String projectName;
    private String description;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;


    @Enumerated(EnumType.STRING)
    private EnumProjectStatus status;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "year_id")
    private Year year;


    //    cascade = ALL giúp thêm/sửa tiêu chí khi thêm/sửa đề tài
    //            orphanRemoval = true giúp khi bạn xóa 1 tiêu chí khỏi list, nó sẽ tự bị xóa khỏi DB
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Criteria> criteria = new ArrayList<>();

}
