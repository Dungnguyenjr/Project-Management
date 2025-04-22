package com.practice.service;

import com.practice.entity.Account;
import com.practice.enums.EnumRole;
import com.practice.req.AccountCreateReq;
import com.practice.req.AccountUpdateReq;
import com.practice.req.StudentCreateReq;
import com.practice.req.TeacherCreateReq;
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
    Account updateTeacher(TeacherCreateReq teacherCreateReq, int id);

    List<Account> getAccountsByRole(EnumRole role);

    List<Account> getUsersWithPagination(int pageIndex, int pageSize);


}
