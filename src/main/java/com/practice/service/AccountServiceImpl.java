package com.practice.service;

import com.practice.entity.*;
import com.practice.enums.EnumGender;
import com.practice.enums.EnumRole;
import com.practice.configuration.JwtProvider;
import com.practice.exception.BadRequestException;
import com.practice.repository.*;
import com.practice.req.*;
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


    @Override
    public Account changePassword(String jwt, ChangePasswordReq changePasswordReq) {
        try {
            Account account = findAccountByJwtToken(jwt);

            if (!passwordEncoder.matches(changePasswordReq.getOldPassword(), account.getPassword())) {
                throw new BadRequestException("Mật khẩu cũ không chính xác");
            }

            // Kiểm tra tính mạnh mẽ của mật khẩu mới
            if (!isValidPassword(changePasswordReq.getNewPassword())) {
                throw new BadRequestException("Mật khẩu mới không hợp lệ");
            }

            account.setPassword(passwordEncoder.encode(changePasswordReq.getNewPassword()));
            accountRepo.save(account);
            return account;

        } catch (Exception e) {
            // Xử lý ngoại lệ tại đây (ví dụ: ghi log, ném ra lỗi chi tiết hơn, v.v.)
            throw new RuntimeException("Đã xảy ra lỗi khi thay đổi mật khẩu: " + e.getMessage(), e);
        }
    }





    //kiểm tra có hợp lệ
    private boolean isValidPassword(String password) {
        return password.length() >= 6 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*");
    }




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
        account.setNote(teacherCreateReq.getNote());
        account.setUuid(UUID.randomUUID().toString());
        account.setTeacherCode(teacherCreateReq.getTeacherCode());

        // Gán role từ request
        account.setRole(teacherCreateReq.getRole());

        // Gán Field
        Field field = fieldRepo.findById(teacherCreateReq.getFieldId())
                .orElseThrow(() -> new RuntimeException("Field not found"));
        account.setField(field);

        // Gán Course
        CourseEntity course = courseRepo.findById(teacherCreateReq.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        account.setCourseEntity(course);

        account.setActive(true);
        return accountRepo.save(account);
    }

    @Override
    public Account updateTeacher(TeacherUpdateReq teacherUpdateReq, int id) {
        Account account = accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        account.setUsername(teacherUpdateReq.getUsername());
        account.setPassword(passwordEncoder.encode(teacherUpdateReq.getPassword()));
        account.setFullName(teacherUpdateReq.getFullName());
        account.setEmail(teacherUpdateReq.getEmail());
        account.setPhone(teacherUpdateReq.getPhone());
        account.setAvatar(teacherUpdateReq.getAvatar());
        account.setBirthday(teacherUpdateReq.getBirthday());
        account.setGender(teacherUpdateReq.getGender());
        account.setAddress(teacherUpdateReq.getAddress());
        account.setNote(teacherUpdateReq.getNote());
        account.setTeacherCode(teacherUpdateReq.getTeacherCode());

        // Gán role và uuid mới (nếu cần)
        account.setRole(teacherUpdateReq.getRole());
        account.setUuid(UUID.randomUUID().toString());

        // Cập nhật Field
        Field field = fieldRepo.findById(teacherUpdateReq.getFieldId())
                .orElseThrow(() -> new RuntimeException("Field not found"));
        account.setField(field);

        // Cập nhật Course
        CourseEntity course = courseRepo.findById(teacherUpdateReq.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        account.setCourseEntity(course);

        return accountRepo.save(account);
    }

    @Override
    public void deleteAccount(int id) {
        Account account = accountRepo.findById(id).get();
        accountRepo.delete(account);
    }


}
