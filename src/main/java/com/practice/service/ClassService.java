package com.practice.service;

import com.practice.dto.ClassDTO;
import com.practice.req.ClassCreateReq;
import com.practice.req.ClassUpdateReq;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClassService {

    List<ClassDTO> findByClassName(String className);
    List<ClassDTO> getAllClass();
    Page<ClassDTO> getPage(int page, int size);
    ClassDTO createClass(ClassCreateReq classCreateReq);
    ClassDTO updateClass(int id, ClassUpdateReq classUpdateReq);
    void deleteClass(int id);
}
