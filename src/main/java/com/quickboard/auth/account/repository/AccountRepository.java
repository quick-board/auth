package com.quickboard.auth.account.repository;

import com.quickboard.auth.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
    Optional<Account> findByRefreshToken(String refreshToken);
    boolean existsByUsername(String username);
}
