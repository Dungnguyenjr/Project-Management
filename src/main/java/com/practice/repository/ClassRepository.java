package com.practice.repository;

import com.practice.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Integer> {

    @Query("select c from ClassEntity c where c.className like %?1%")
    List<ClassEntity> findByClassName(String className);

}
