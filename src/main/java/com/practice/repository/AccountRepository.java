package com.practice.repository;

import com.practice.entity.Account;
import com.practice.enums.EnumRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);

    Page<Account> findAll(Pageable pageable);
    List<Account> findByRole(EnumRole role);


}
