package com.practice.repository;

import com.practice.entity.GroupReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupReportRepository extends JpaRepository<GroupReport, Long> {
    List<GroupReport> findByGroup_Id(Long groupId);

}
