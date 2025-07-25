package com.quickboard.auth.account.entity;

import com.quickboard.auth.account.enums.AccountState;
import com.quickboard.auth.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "accounts")
@Getter
@NoArgsConstructor
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Setter
    private String username;

    @Column(nullable = false)
    @Setter
    private String password;

    @Column(name = "account_state")
    @Enumerated(EnumType.STRING)
    @Setter
    private AccountState accountState;

    @Column(name = "refresh_token")
    @Setter
    private String refreshToken;

    @Column(name = "refresh_expires_at")
    @Setter
    private Instant refreshExpiresAt;

    @Builder
    public Account(String username, String password, AccountState accountState) {
        this.username = username;
        this.password = password;
        this.accountState = accountState;
    }
}
