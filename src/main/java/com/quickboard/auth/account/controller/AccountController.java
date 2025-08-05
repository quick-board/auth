package com.quickboard.auth.account.controller;

import com.quickboard.auth.account.dto.AccountCreate;
import com.quickboard.auth.account.dto.AccountStatePatch;
import com.quickboard.auth.account.service.AccountService;
import com.quickboard.auth.common.argumentresolver.annotations.CurrentUserId;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public void postAccount(@RequestBody AccountCreate accountCreate){
        accountService.createAccount(accountCreate);
    }

    @PatchMapping("/accounts/me/inactive")
    public void inactiveMyAccount(){
        //1. 컨텍스트 홀더에서 회원 식별 정보 가져오기
        //2. 상태변경
    }

    @PatchMapping("/accounts/{id}/state")
    public void changeStateByAdmin(@PathVariable("id") Long accountsId,
                                   @RequestBody AccountStatePatch accountStatePatch){

    }


    //익명 사용자를 가져오려면 @CurrentSecurityContext써야함. todo argumentResolver 만들기
    @GetMapping("/test")
    public String test(@CurrentUserId Optional<Long> optionalUserId){
        log.info(optionalUserId.toString());
        return optionalUserId.toString();
    }
}
