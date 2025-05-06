package com.practice.service;

import com.practice.entity.BatchEntity;
import com.practice.repository.BatchRepository;
import com.practice.req.BatchCreatReq;
import com.practice.req.BatchUpdateReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BatchServiceImplTest {

    @InjectMocks
    private BatchServiceImpl batchService;

    @Mock
    private BatchRepository batchRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBatch() {
        BatchCreatReq req = new BatchCreatReq();
        req.setName("Batch 2025");
        req.setYear("2025");
        req.setDateStart(LocalDate.of(2025, 1, 1));
        req.setDateEnd(LocalDate.of(2025, 12, 31));

        BatchEntity savedEntity = new BatchEntity();
        savedEntity.setId(1L);
        savedEntity.setName(req.getName());
        savedEntity.setYear(req.getYear());
        savedEntity.setDateStart(req.getDateStart());
        savedEntity.setDateEnd(req.getDateEnd());

        when(batchRepo.save(any(BatchEntity.class))).thenReturn(savedEntity);

        BatchEntity result = batchService.createBatch(req);

        assertNotNull(result);
        assertEquals("Batch 2025", result.getName());
        verify(batchRepo, times(1)).save(any(BatchEntity.class));
    }

    @Test
    void testUpdateBatch() {
        BatchUpdateReq req = new BatchUpdateReq();
        req.setName("Updated Batch");
        req.setYear("2026");
        req.setDateStart(LocalDate.of(2026, 1, 1));
        req.setDateEnd(LocalDate.of(2026, 12, 31));

        BatchEntity updatedEntity = new BatchEntity();
        updatedEntity.setId(1L);
        updatedEntity.setName(req.getName());
        updatedEntity.setYear(req.getYear());
        updatedEntity.setDateStart(req.getDateStart());
        updatedEntity.setDateEnd(req.getDateEnd());

        when(batchRepo.save(any(BatchEntity.class))).thenReturn(updatedEntity);

        BatchEntity result = batchService.updateBatch(1L, req);

        assertNotNull(result);
        assertEquals("Updated Batch", result.getName());
        verify(batchRepo).save(any(BatchEntity.class));
    }

    @Test
    void testSearch_withKeyword() {
        String name = "Spring";
        List<BatchEntity> mockList = Arrays.asList(new BatchEntity(), new BatchEntity());
        when(batchRepo.searchNameBatch("%" + name + "%")).thenReturn(mockList);

        List<BatchEntity> result = batchService.searchNameBatch(name);

        assertEquals(2, result.size());
        verify(batchRepo).searchNameBatch("%" + name + "%");
    }

    @Test
    void testSearch_emptyKeyword() {
        List<BatchEntity> mockList = List.of(new BatchEntity());
        when(batchRepo.findAll()).thenReturn(mockList);

        List<BatchEntity> result = batchService.searchNameBatch("");

        assertEquals(1, result.size());
        verify(batchRepo).findAll();
    }

    @Test
    void testBatchByYear() {
        String year = "2025";
        when(batchRepo.findBatchEntitiesByYear(year)).thenReturn(List.of(new BatchEntity()));

        List<BatchEntity> result = batchService.getBatchByYear(year);

        assertEquals(1, result.size());
        verify(batchRepo).findBatchEntitiesByYear(year);
    }

    @Test
    void testBatchByName() {
        String name = "Java";
        when(batchRepo.findBatchEntitiesByName(name)).thenReturn(List.of(new BatchEntity()));

        List<BatchEntity> result = batchService.getBatchByName(name);

        assertEquals(1, result.size());
        verify(batchRepo).findBatchEntitiesByName(name);
    }

    @Test
    void testIdCheck() {
        Long id = 1L;
        BatchEntity entity = new BatchEntity();
        entity.setId(id);
        when(batchRepo.findBatchEntitiesById(id)).thenReturn(entity);

        BatchEntity result = batchService.idCheck(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(batchRepo).findBatchEntitiesById(id);
    }

    @Test
    void testDeleteBatch() {
        Long id = 1L;

        doNothing().when(batchRepo).deleteById(id);

        batchService.deleteBatch(id);

        verify(batchRepo).deleteById(id);
    }
}
