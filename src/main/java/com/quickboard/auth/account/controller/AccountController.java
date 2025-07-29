package com.quickboard.auth.account.controller;

import com.quickboard.auth.account.dto.AccountCreate;
import com.quickboard.auth.account.dto.AccountStatePatch;
import com.quickboard.auth.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
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
}
