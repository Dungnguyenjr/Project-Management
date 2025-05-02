package com.practice.repository;

import com.practice.entity.GroupTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupTaskRepository  extends JpaRepository<GroupTask, Long> {
}
