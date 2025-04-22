package com.practice.service;

import com.practice.entity.BatchEntity;
import com.practice.repository.BatchRepository;
import com.practice.req.BatchReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BatchServiceImpl implements BatchService {
    @Autowired
    BatchRepository batchRepo;

    @Override
    public BatchEntity idCheck(Long id) {
        return batchRepo.findBatchEntitiesById(id);
    }

    @Override
    public  BatchEntity getBatchByYear(String year) {
        return batchRepo.findBatchEntitiesByYear(year);
    }

    @Override
    public BatchEntity getBatchByName(String name) {
        return batchRepo.findBatchEntitiesByName(name);
    }

    @Override
    public BatchEntity createBatch(BatchReq request) {
        try {
            BatchEntity batchEntity = new BatchEntity();
            batchEntity.setName(request.getName());
            batchEntity.setYear(request.getYear());
            batchEntity.setDateStart(request.getDateStart());
            batchEntity.setDateEnd(request.getDateEnd());

            return batchRepo.save(batchEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public BatchEntity updateBatch(Long id,BatchReq request) {
        try {
            BatchEntity batchEntity = new BatchEntity();
            batchEntity.setId(id);
            batchEntity.setName(request.getName());
            batchEntity.setYear(request.getYear());
            batchEntity.setDateStart(request.getDateStart());
            batchEntity.setDateEnd(request.getDateEnd());

            return batchRepo.save(batchEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteBatch(Long id) {
        batchRepo.deleteById(id);
    }

    @Override
    public List<BatchEntity> searchNameBatch(String nameBatch) {
        if (StringUtils.isEmpty(nameBatch)){
            return batchRepo.findAll();
        } else {
            nameBatch = "%" + nameBatch + "%";
            return batchRepo.searchNameBatch(nameBatch);
        }
    }
}
