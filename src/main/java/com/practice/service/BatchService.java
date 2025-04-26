package com.practice.service;

import com.practice.req.BatchCreatReq;
import com.practice.entity.BatchEntity;
import com.practice.req.BatchUpdateReq;

import java.util.List;

public interface BatchService {
    BatchEntity idCheck(Long id);

    BatchEntity createBatch(BatchCreatReq batchCreatReq);

    BatchEntity getBatchByYear(String year);

    BatchEntity getBatchByName(String name);

    BatchEntity updateBatch(Long id, BatchUpdateReq batchUpdateReq);

    void deleteBatch(Long id);

    List<BatchEntity> searchNameBatch(String nameBatch);

}
