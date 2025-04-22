package com.practice.repository;

import com.practice.entity.BatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<BatchEntity, Long> {
    BatchEntity findBatchEntitiesById(Long id);

    BatchEntity findBatchEntitiesByYear(String year);

    BatchEntity findBatchEntitiesByName(String name);

    @Query(value = "select * from batch where name like :name", nativeQuery = true)
    List<BatchEntity> searchNameBatch(String name);
}
