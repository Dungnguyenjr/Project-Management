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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

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
        StudentCreateReq studentCreateReq = new StudentCreateReq();
        studentCreateReq.setUsername("testuser");
        studentCreateReq.setPassword("password");
        studentCreateReq.setFullName("Test User");
        studentCreateReq.setEmail("testuser@example.com");
        studentCreateReq.setPhone("123456789");
        studentCreateReq.setRole(EnumRole.STUDENT);
        studentCreateReq.setAvatar("avatar.png");
        studentCreateReq.setUuid(UUID.randomUUID().toString());
        studentCreateReq.setBirthday(new Date());
        studentCreateReq.setGender(EnumGender.MALE);
        studentCreateReq.setAddress("Test Address");
        studentCreateReq.setNote("Test note");
        studentCreateReq.setStudentCode("S12345");
        studentCreateReq.setFieldId(1L);
        studentCreateReq.setClassEntityId(1);
        studentCreateReq.setBatchEntityId(1L);


        Field field = new Field();
        ClassEntity classEntity = new ClassEntity();
        BatchEntity batchEntity = new BatchEntity();

        when(fieldRepo.findById(1L)).thenReturn(Optional.of(field));
        when(classRepo.findById(1)).thenReturn(Optional.of(classEntity));
        when(batchRepo.findById(1L)).thenReturn(Optional.of(batchEntity));
        when(passwordEncoder.encode("password")).thenReturn("$2a$12$1fF5H5k7.kGPJr.rRAXMcuF3z3pEhPkv.I5lR4ZNnpFRtnhCBNJ0y");

        // Tạo đối tượng Account sau khi save
        Account accountResult = new Account();
        accountResult.setId(1);
        accountResult.setUsername(studentCreateReq.getUsername());
        accountResult.setPassword("$2a$12$1fF5H5k7.kGPJr.rRAXMcuF3z3pEhPkv.I5lR4ZNnpFRtnhCBNJ0y");
        accountResult.setFullName(studentCreateReq.getFullName());
        accountResult.setEmail(studentCreateReq.getEmail());
        accountResult.setPhone(studentCreateReq.getPhone());
        accountResult.setAvatar(studentCreateReq.getAvatar());
        accountResult.setBirthday(studentCreateReq.getBirthday());
        accountResult.setAddress(studentCreateReq.getAddress());
        accountResult.setUuid(UUID.randomUUID().toString());
        accountResult.setStudentCode(studentCreateReq.getStudentCode());
        accountResult.setField(field);
        accountResult.setClassEntity(classEntity);
        accountResult.setBatchEntity(batchEntity);
        accountResult.setActive(true);

        when(accountRepo.save(Mockito.any(Account.class))).thenReturn(accountResult);


        String Password = "312sd7893knkfnkdkfsk998e20398d1209312093u1bdsdsdbd";
        String Username = "teststd";
        String Phone = "123456789";
        String Email = "testuser@email.com";
        Account savedAccount = accountService.saveAccount(studentCreateReq);


        assertNotNull(savedAccount);
        assertEquals(Password, savedAccount.getPassword());
        assertEquals(Username, savedAccount.getUsername());
        assertEquals(Phone, savedAccount.getPhone());
        assertEquals(Email, savedAccount.getEmail());
    }

}
