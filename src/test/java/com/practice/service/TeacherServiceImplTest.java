package com.practice.service;

import com.practice.entity.Account;
import com.practice.entity.CourseEntity;
import com.practice.entity.Field;
import com.practice.enums.EnumGender;
import com.practice.enums.EnumRole;
import com.practice.repository.AccountRepository;
import com.practice.repository.CourseRepository;
import com.practice.repository.FieldRepository;
import com.practice.req.TeacherCreateReq;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceImplTest {

    @Mock
    private AccountRepository accountRepo;

    @Mock
    private FieldRepository fieldRepo;

    @Mock
    private CourseRepository courseRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void createTeacherTest() {
        // Setup request
        TeacherCreateReq req = new TeacherCreateReq();
        req.setUsername("teachertest");
        req.setPassword("securepass");
        req.setFullName("Teacher Test");
        req.setEmail("teacher@example.com");
        req.setPhone("987654321");
        req.setRole(EnumRole.TEACHER);
        req.setAvatar("avatar_teacher.png");
        req.setUuid(UUID.randomUUID().toString());
        req.setBirthday(new Date());
        req.setGender(EnumGender.FEMALE);
        req.setAddress("Teacher Address");
        req.setNote("This is a note.");
        req.setTeacherCode("T56789");
        req.setFieldId(2L);
        req.setCourseId(3);

        // Mock related entities
        Field field = new Field();
        CourseEntity course = new CourseEntity();

        when(fieldRepo.findById(2L)).thenReturn(Optional.of(field));
        when(courseRepo.findById(3)).thenReturn(Optional.of(course));
        when(passwordEncoder.encode("securepass")).thenReturn("$2a$10$encodedPassword");

        // Expected account result
        Account savedEntity = new Account();
        savedEntity.setId(2);
        savedEntity.setUsername(req.getUsername());
        savedEntity.setPassword("$2a$10$encodedPassword");
        savedEntity.setFullName(req.getFullName());
        savedEntity.setEmail(req.getEmail());
        savedEntity.setPhone(req.getPhone());
        savedEntity.setAvatar(req.getAvatar());
        savedEntity.setBirthday(req.getBirthday());
        savedEntity.setAddress(req.getAddress());
        savedEntity.setUuid(req.getUuid());
        savedEntity.setTeacherCode(req.getTeacherCode());
        savedEntity.setField(field);
        savedEntity.setCourseEntity(course);
        savedEntity.setActive(true);

        when(accountRepo.save(Mockito.any(Account.class))).thenReturn(savedEntity);

        // Act
        Account savedAccount = accountService.createTeacher(req);

        // Assert
        assertNotNull(savedAccount);
        assertEquals(req.getUsername(), savedAccount.getUsername());
        assertEquals(req.getEmail(), savedAccount.getEmail());
        assertEquals(req.getPhone(), savedAccount.getPhone());
        assertEquals(req.getFullName(), savedAccount.getFullName());
        assertEquals("$2a$10$encodedPassword", savedAccount.getPassword()); // check password is encoded
        assertEquals("T56789", savedAccount.getTeacherCode());
        assertEquals(field, savedAccount.getField());
        assertEquals(course, savedAccount.getCourseEntity());
    }
}
