package com.practice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.practice.enums.EnumGender;
import com.practice.enums.EnumRole;
import com.practice.enums.EnumRoleMember;
import com.practice.enums.EnumTeacherType;
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

    // ngành nghề
    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    //lớp
    @ManyToOne
    @JoinColumn(name = "classEntity_id")
    private ClassEntity classEntity;

    //khoá học
    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity courseEntity;


    // giáo viên

    private String teacherCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "teacher_type")
    private EnumTeacherType teacherType;



    @ManyToOne
    @JoinColumn(name = "batchEntity_id")
    private BatchEntity batchEntity;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "studentRef")
    private List<GroupMemberDetail> groupMemberships;


    @Enumerated(EnumType.STRING)
    @Column(name = "enumRoleMember")
    private EnumRoleMember enumRoleMember;




}
