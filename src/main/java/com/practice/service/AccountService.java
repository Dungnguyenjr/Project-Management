package com.practice.service;

import com.practice.entity.Account;
import com.practice.enums.EnumRole;
import com.practice.req.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AccountService extends UserDetailsService {

    Account createAccount(AccountCreateReq accountCreateReq);
    Account findAccountByJwtToken(String jwt) throws Exception;
    Account findByUsername(String username);
    Account updateAccount(AccountUpdateReq accountUpdateReq , int id);

    Account saveAccount(StudentCreateReq studentCreateReq);
    Account updateStudent(StudentCreateReq studentCreateReq, int id);

    void deleteAccount(int id);

    Account createTeacher(TeacherCreateReq teacherCreateReq);
    Account updateTeacher(TeacherUpdateReq teacherUpdateReq, int id);

    List<Account> getAccountsByRole(EnumRole role);

    List<Account> getUsersWithPagination(int pageIndex, int pageSize);

    Account changePassword(String jwt, ChangePasswordReq changePasswordReq);







}
