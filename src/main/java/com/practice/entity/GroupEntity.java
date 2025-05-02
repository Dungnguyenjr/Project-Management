package com.practice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "`groups`")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "batch_id") // liên kết tới đợt
    private BatchEntity batch;

    @Column(name = "group_name")
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "project_id") // liên kết tới đề tài
    private Project project;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<GroupMemberDetail> members;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GroupTask> groupTasks;


    public int getStudentCount() {
        if (members == null) return 0;
        return (int) members.stream()
                .filter(detail -> detail.getStudent() != null && "STUDENT".equals(detail.getStudent().getRole().name()))
                .count();
    }
}
