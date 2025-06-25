package com.quickboard.auth.account.repository;

import com.quickboard.auth.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
