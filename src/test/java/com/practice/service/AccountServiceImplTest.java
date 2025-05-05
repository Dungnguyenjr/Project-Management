package com.practice.service;

import com.practice.entity.Account;
import com.practice.enums.EnumGender;
import com.practice.repository.AccountRepository;
import com.practice.enums.EnumRole;
import com.practice.req.AccountCreateReq;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void createAccountTest() {
        // Setup request
        AccountCreateReq req = new AccountCreateReq();
        req.setUsername("userTest");
        req.setPassword("securePassword123");
        req.setFullName("User Test");
        req.setEmail("usertest@example.com");
        req.setPhone("1234567890");
        req.setAddress("Test Address");
        req.setGender("MALE");
        req.setRole(EnumRole.ADMIN.name());
        req.setAvatar("avatar_user.png");
        req.setActive(true);
        req.setUuid(UUID.randomUUID().toString());
        req.setBirthday(new Date());
        req.setNote("This is a note.");
        req.setStudyAndWork("Studying and working at Company XYZ");


        when(passwordEncoder.encode("securePassword123")).thenReturn("$2a$10$encodedPassword123");


        Account savedEntity = new Account();
        savedEntity.setId(1);
        savedEntity.setUsername(req.getUsername());
        savedEntity.setPassword("$2a$10$encodedPassword123");
        savedEntity.setFullName(req.getFullName());
        savedEntity.setEmail(req.getEmail());
        savedEntity.setPhone(req.getPhone());
        savedEntity.setAddress(req.getAddress());
        savedEntity.setGender(EnumGender.MALE);
        savedEntity.setRole(EnumRole.ADMIN);
        savedEntity.setAvatar(req.getAvatar());
        savedEntity.setActive(req.isActive());
        savedEntity.setUuid(req.getUuid());
        savedEntity.setBirthday(req.getBirthday());
        savedEntity.setNote(req.getNote());
        savedEntity.setStudyAndWork(req.getStudyAndWork());

        when(accountRepo.save(Mockito.any(Account.class))).thenReturn(savedEntity);

        // Act
        Account savedAccount = accountService.createAccount(req);

        // Assert
        assertNotNull(savedAccount);
        assertEquals(req.getUsername(), savedAccount.getUsername());
        assertEquals(req.getEmail(), savedAccount.getEmail());
        assertEquals(req.getPhone(), savedAccount.getPhone());
        assertEquals(req.getFullName(), savedAccount.getFullName());
        assertEquals("$2a$10$encodedPassword123", savedAccount.getPassword());
        assertEquals(EnumGender.MALE, savedAccount.getGender());
        assertEquals(EnumRole.ADMIN, savedAccount.getRole());
        assertEquals(req.getAvatar(), savedAccount.getAvatar());
        assertEquals(req.isActive(), savedAccount.isActive());
        assertEquals(req.getUuid(), savedAccount.getUuid());
        assertEquals(req.getBirthday(), savedAccount.getBirthday());
        assertEquals(req.getNote(), savedAccount.getNote());
        assertEquals(req.getStudyAndWork(), savedAccount.getStudyAndWork());
    }
}
