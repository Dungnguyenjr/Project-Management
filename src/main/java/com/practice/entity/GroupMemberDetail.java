package com.practice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "group_member_detail", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"group_id", "student_id"})
})
public class GroupMemberDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private GroupEntity group;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account student;

    private String individualReport;  // Báo cáo cá nhân
    private Float personalScore;
    private Float groupScore;

}
