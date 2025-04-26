package com.practice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.practice.enums.EnumGender;
import com.practice.enums.EnumRole;
import com.practice.enums.EnumRoleMember;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(length = 11)
    private String phone;

    private String address;

    @Enumerated(EnumType.STRING)
    private EnumGender gender;

    @Enumerated(EnumType.STRING)
    private EnumRole role;

    private String avatar;

    private boolean isActive;

    @Column(length = 36)
    private String uuid;

    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;

    private String note;

    private String studyAndWork;

    //msv
    private String studentCode;

    //chuyen ngành
    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    //lớp
    @ManyToOne
    @JoinColumn(name = "classEntity_id")
    private ClassEntity classEntity;

    //khoá học
    @ManyToOne
    @JoinColumn(name = "batchEntity_id")
    private BatchEntity batchEntity;

    // giáo viên

    private String teacherCode;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity courseEntity;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "studentRef")
    private List<GroupMemberDetail> groupMemberships;


    @Enumerated(EnumType.STRING)
    @Column(name = "enumRoleMember")
    private EnumRoleMember enumRoleMember;




}
