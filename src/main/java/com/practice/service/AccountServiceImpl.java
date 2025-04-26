package com.practice.service;

import com.practice.entity.*;
import com.practice.enums.EnumGender;
import com.practice.enums.EnumRole;
import com.practice.configuration.JwtProvider;
import com.practice.repository.*;
import com.practice.req.AccountCreateReq;
import com.practice.req.AccountUpdateReq;
import com.practice.req.StudentCreateReq;
import com.practice.req.TeacherCreateReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private final AccountRepository accountRepo;

    private final FieldRepository fieldRepo;
    private final ClassRepository classRepo;
    private final BatchRepository batchRepo;
    private final CourseRepository courseRepo;

    public AccountServiceImpl(PasswordEncoder passwordEncoder, JwtProvider jwtProvider, AccountRepository accountRepo,
                              FieldRepository fieldRepo, ClassRepository classRepo,
                              BatchRepository batchRepo, CourseRepository courseRepo) {
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.accountRepo = accountRepo;

        this.fieldRepo = fieldRepo;
        this.classRepo = classRepo;
        this.batchRepo = batchRepo;
        this.courseRepo = courseRepo;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Account account = accountRepo.findByUsername(username);
//
//        if (account == null) {
//            throw new UsernameNotFoundException("Account Not Found");
//        }
//
//        return new User(username, account.getPassword(),
//                AuthorityUtils.createAuthorityList("ROLE_" + account.getRole()));
//    }
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = accountRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Account not found"));

    return new User(username, account.getPassword(),
            AuthorityUtils.createAuthorityList("ROLE_" + account.getRole()));
}

    @Override
    public List<Account> getAccountsByRole(EnumRole role) {
        return accountRepo.findByRole(role);
    }


    @Override
    public Account createAccount(AccountCreateReq accountCreateReq) {
        Account account = new Account();
        account.setUsername(accountCreateReq.getUsername());
        account.setPassword(passwordEncoder.encode(accountCreateReq.getPassword()));
        account.setFullName(accountCreateReq.getFullName());
        account.setEmail(accountCreateReq.getEmail());
        account.setAddress(accountCreateReq.getAddress());
        account.setGender(EnumGender.valueOf(accountCreateReq.getGender()));
        account.setRole(EnumRole.valueOf(accountCreateReq.getRole()));
        account.setAvatar(accountCreateReq.getAvatar());
        account.setActive(accountCreateReq.isActive());
        account.setUuid(UUID.randomUUID().toString());
        account.setBirthday(accountCreateReq.getBirthday());
        return accountRepo.save(account);
    }

    @Override
    public Account findAccountByJwtToken(String jwt) throws Exception {
        String username = jwtProvider.getEmailFromJwtToken(jwt);
        return findByUsername(username);
    }
    @Override
    public List<Account> getUsersWithPagination(int pageIndex, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
        Page<Account> page = accountRepo.findAll(pageRequest);
        return page.getContent();
    }

//    @Override
//    public Account findByUsername(String username) {
//        Account account = accountRepo.findByUsername(username);
//        return account;
//    }
    @Override
    public Account findByUsername(String username) {
        Account account = accountRepo.findByUsername(username) .orElse(null);
        return account;
    }

    @Override
    public Account updateAccount(AccountUpdateReq accountUpdateReq, int id) {
        Account account = accountRepo.findById(id).get();
        account.setUsername(accountUpdateReq.getUsername());
        account.setPassword(passwordEncoder.encode(accountUpdateReq.getPassword()));
        account.setFullName(accountUpdateReq.getFullName());
        account.setEmail(accountUpdateReq.getEmail());
        account.setAddress(accountUpdateReq.getAddress());
        account.setGender(EnumGender.valueOf(accountUpdateReq.getGender()));
        account.setRole(EnumRole.valueOf(accountUpdateReq.getRole()));
        account.setAvatar(accountUpdateReq.getAvatar());
        account.setActive(accountUpdateReq.isActive());
        account.setUuid(UUID.randomUUID().toString());
        account.setBirthday(accountUpdateReq.getBirthday());
        return accountRepo.save(account);
    }

    //Student
    @Override
    public Account saveAccount(StudentCreateReq studentCreateReq) {
        Account account = new Account();
        account.setUsername(studentCreateReq.getUsername());
        account.setPassword(passwordEncoder.encode(studentCreateReq.getPassword()));
        account.setFullName(studentCreateReq.getFullName());
        account.setEmail(studentCreateReq.getEmail());
        account.setPhone(studentCreateReq.getPhone());
        account.setAvatar(studentCreateReq.getAvatar());
        account.setBirthday(studentCreateReq.getBirthday());
        account.setGender(studentCreateReq.getGender());
        account.setAddress(studentCreateReq.getAddress());
        account.setUuid(UUID.randomUUID().toString());
        account.setNote(studentCreateReq.getNote());
        account.setStudentCode(studentCreateReq.getStudentCode());

        // Gán role từ request
        account.setRole(studentCreateReq.getRole());

        // Tìm Field
        Field field = fieldRepo.findById(studentCreateReq.getFieldId())
                .orElseThrow(() -> new RuntimeException("Industry not found"));
        account.setField(field);

        // Tìm ClassEntity
        ClassEntity classEntity = classRepo.findById(studentCreateReq.getClassEntityId())
                .orElseThrow(() -> new RuntimeException("Class not found"));
        account.setClassEntity(classEntity);

        // Tìm BatchEntity
        BatchEntity batchEntity = batchRepo.findById(studentCreateReq.getBatchEntityId())
                .orElseThrow(() -> new RuntimeException("Batch not found"));
        account.setBatchEntity(batchEntity);

        account.setActive(true);
        return accountRepo.save(account);
    }

    @Override
    public Account updateStudent(StudentCreateReq studentCreateReq, int id) {
        Account account = accountRepo.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));

        account.setUsername(studentCreateReq.getUsername());
        account.setPassword(passwordEncoder.encode(studentCreateReq.getPassword()));
        account.setFullName(studentCreateReq.getFullName());
        account.setEmail(studentCreateReq.getEmail());
        account.setPhone(studentCreateReq.getPhone());
        account.setAvatar(studentCreateReq.getAvatar());
        account.setBirthday(studentCreateReq.getBirthday());
        account.setGender(studentCreateReq.getGender());
        account.setAddress(studentCreateReq.getAddress());
        account.setNote(studentCreateReq.getNote());
        account.setStudentCode(studentCreateReq.getStudentCode());

        // Cập nhật mối quan hệ
        Field field = fieldRepo.findById(studentCreateReq.getFieldId())
                .orElseThrow(() -> new RuntimeException("Field not found"));
        account.setField(field);

        ClassEntity classEntity = classRepo.findById(studentCreateReq.getClassEntityId())
                .orElseThrow(() -> new RuntimeException("Class not found"));
        account.setClassEntity(classEntity);

        BatchEntity batchEntity = batchRepo.findById(studentCreateReq.getBatchEntityId())
                .orElseThrow(() -> new RuntimeException("Batch not found"));
        account.setBatchEntity(batchEntity);

        return accountRepo.save(account);
    }

    //teacher
    @Override
    public Account createTeacher(TeacherCreateReq teacherCreateReq) {
        Account account = new Account();
        account.setUsername(teacherCreateReq.getUsername());
        account.setPassword(passwordEncoder.encode(teacherCreateReq.getPassword()));
        account.setFullName(teacherCreateReq.getFullName());
        account.setEmail(teacherCreateReq.getEmail());
        account.setPhone(teacherCreateReq.getPhone());
        account.setAvatar(teacherCreateReq.getAvatar());
        account.setBirthday(teacherCreateReq.getBirthday());
        account.setGender(teacherCreateReq.getGender());
        account.setAddress(teacherCreateReq.getAddress());
        account.setUuid(UUID.randomUUID().toString());
        account.setNote(teacherCreateReq.getNote());
        account.setStudentCode(teacherCreateReq.getTeacherCode());

        // Gán role từ request
        account.setRole(teacherCreateReq.getRole());

        // Tìm Field
        Field field = fieldRepo.findById(teacherCreateReq.getFieldId())
                .orElseThrow(() -> new RuntimeException("Industry not found"));
        account.setField(field);

        CourseEntity courseEntity = courseRepo.findById(teacherCreateReq.getCourseId())
                .orElseThrow(() -> new RuntimeException("course not found"));
        account.setCourseEntity(courseEntity);


        account.setActive(true);
        return accountRepo.save(account);
    }

    @Override
    public Account updateTeacher(TeacherCreateReq teacherCreateReq, int id) {
        Account account = accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        // Update teacher fields
        account.setUsername(teacherCreateReq.getUsername());
        account.setPassword(passwordEncoder.encode(teacherCreateReq.getPassword()));
        account.setFullName(teacherCreateReq.getFullName());
        account.setEmail(teacherCreateReq.getEmail());
        account.setPhone(teacherCreateReq.getPhone());
        account.setAvatar(teacherCreateReq.getAvatar());
        account.setBirthday(teacherCreateReq.getBirthday());
        account.setGender(teacherCreateReq.getGender());
        account.setAddress(teacherCreateReq.getAddress());
        account.setNote(teacherCreateReq.getNote());
        account.setStudentCode(teacherCreateReq.getTeacherCode());

        // Update role
        account.setRole(teacherCreateReq.getRole());

        // Update relationships
        Field field = fieldRepo.findById(teacherCreateReq.getFieldId())
                .orElseThrow(() -> new RuntimeException("Industry not found"));
        account.setField(field);

        // Update course
        CourseEntity courseEntity = courseRepo.findById(teacherCreateReq.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        account.setCourseEntity(courseEntity);

        return accountRepo.save(account);
    }

    @Override
    public void deleteAccount(int id) {
        Account account = accountRepo.findById(id).get();
        accountRepo.delete(account);
    }


}
