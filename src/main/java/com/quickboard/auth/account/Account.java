package com.quickboard.auth.account;

import com.quickboard.auth.account.enums.AccountState;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@Getter
@NoArgsConstructor
public class Account {
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

    @Builder
    public Account(String username, String password, AccountState accountState) {
        this.username = username;
        this.password = password;
        this.accountState = accountState;
    }
}
