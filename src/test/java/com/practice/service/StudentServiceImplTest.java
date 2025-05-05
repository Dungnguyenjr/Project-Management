package com.practice.service;

import com.practice.entity.Account;
import com.practice.entity.BatchEntity;
import com.practice.entity.ClassEntity;
import com.practice.entity.Field;
import com.practice.enums.EnumGender;
import com.practice.enums.EnumRole;
import com.practice.repository.AccountRepository;
import com.practice.repository.BatchRepository;
import com.practice.repository.ClassRepository;
import com.practice.repository.FieldRepository;
import com.practice.req.StudentCreateReq;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @Mock
    private AccountRepository accountRepo;

    @Mock
    private FieldRepository fieldRepo;

    @Mock
    private ClassRepository classRepo;

    @Mock
    private BatchRepository batchRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void saveAccountTest() {
        // Setup request
        StudentCreateReq req = new StudentCreateReq();
        req.setUsername("testuser");
        req.setPassword("password");
        req.setFullName("Test User");
        req.setEmail("testuser@example.com");
        req.setPhone("123456789");
        req.setRole(EnumRole.STUDENT);
        req.setAvatar("avatar.png");
        req.setUuid(UUID.randomUUID().toString());
        req.setBirthday(new Date());
        req.setGender(EnumGender.MALE);
        req.setAddress("Test Address");
        req.setNote("Test note");
        req.setStudentCode("S12345");
        req.setFieldId(1L);
        req.setClassEntityId(1);
        req.setBatchEntityId(1L);

        // Setup entities
        Field field = new Field();
        ClassEntity classEntity = new ClassEntity();
        BatchEntity batchEntity = new BatchEntity();

        // Mock repositories
        when(fieldRepo.findById(1L)).thenReturn(Optional.of(field));
        when(classRepo.findById(1)).thenReturn(Optional.of(classEntity));
        when(batchRepo.findById(1L)).thenReturn(Optional.of(batchEntity));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Expected account result
        Account savedEntity = new Account();
        savedEntity.setId(1);
        savedEntity.setUsername(req.getUsername());
        savedEntity.setPassword("encodedPassword");
        savedEntity.setFullName(req.getFullName());
        savedEntity.setEmail(req.getEmail());
        savedEntity.setPhone(req.getPhone());
        savedEntity.setAvatar(req.getAvatar());
        savedEntity.setBirthday(req.getBirthday());
        savedEntity.setAddress(req.getAddress());
        savedEntity.setUuid(req.getUuid());
        savedEntity.setStudentCode(req.getStudentCode());
        savedEntity.setField(field);
        savedEntity.setClassEntity(classEntity);
        savedEntity.setBatchEntity(batchEntity);
        savedEntity.setActive(true);

        when(accountRepo.save(any(Account.class))).thenReturn(savedEntity);

        // Act
        Account savedAccount = accountService.saveAccount(req);

        // Assert
        assertNotNull(savedAccount);
        assertEquals(req.getUsername(), savedAccount.getUsername());
        assertEquals(req.getEmail(), savedAccount.getEmail());
        assertEquals(req.getPhone(), savedAccount.getPhone());
        assertEquals(req.getFullName(), savedAccount.getFullName());
        assertEquals("encodedPassword", savedAccount.getPassword()); // check password is encoded
        assertEquals("S12345", savedAccount.getStudentCode());
        assertEquals(field, savedAccount.getField());
        assertEquals(classEntity, savedAccount.getClassEntity());
        assertEquals(batchEntity, savedAccount.getBatchEntity());
    }
}
