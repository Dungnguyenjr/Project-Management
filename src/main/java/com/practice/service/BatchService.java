package com.practice.service;

import com.practice.req.BatchReq;
import com.practice.entity.BatchEntity;
import java.util.List;

public interface BatchService {
    BatchEntity idCheck(Long id);

    BatchEntity createBatch(BatchReq request);

    BatchEntity getBatchByYear(String year);

    BatchEntity getBatchByName(String name);

    BatchEntity updateBatch(Long id, BatchReq request);

    void deleteBatch(Long id);

    List<BatchEntity> searchNameBatch(String nameBatch);

}
