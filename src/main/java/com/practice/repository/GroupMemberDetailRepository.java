package com.practice.repository;

import com.practice.entity.Account;
import com.practice.entity.GroupEntity;
import com.practice.entity.GroupMemberDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMemberDetailRepository extends JpaRepository<GroupMemberDetail, Long> {
    List<GroupMemberDetail> findByGroup_Id(Long groupId);
    Optional<GroupMemberDetail> findByStudent_Id(Long studentId);
    Optional<GroupMemberDetail> findByGroupAndStudent(GroupEntity group, Account student);
    List<GroupMemberDetail> findAllByGroup_Id(Long groupId);

    boolean existsByGroupAndStudent(GroupEntity group, Account student);
}
