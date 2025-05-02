package com.practice.repository;

import com.practice.entity.GroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
    @Query("SELECT g FROM GroupEntity g JOIN FETCH g.batch JOIN FETCH g.project")
    Page<GroupEntity> findAllGroupsWithBatchAndProject(Pageable pageable);

}
