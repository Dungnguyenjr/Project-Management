package com.practice.entity;

import com.practice.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table
public class TaskAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private GroupTask groupTask;

    @ManyToOne
    private Account account; // LIÊN KẾT ACCOUNT ĐỂ LẤY THÔNG TINN SINH VIÊN

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private String note;
}
