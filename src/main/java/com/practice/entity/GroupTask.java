package com.practice.entity;

import com.practice.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Data
public class GroupTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskName;
    private LocalDate startDate;
    private LocalDate endDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    private GroupEntity group;

    @OneToMany(mappedBy = "groupTask", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskAssignment> assignments;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;
}

