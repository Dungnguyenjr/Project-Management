package com.practice.repository;

import com.practice.entity.GroupReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupReportRepository extends JpaRepository<GroupReport, Long> {
    List<GroupReport> findByGroup_Id(Long groupId);
}
