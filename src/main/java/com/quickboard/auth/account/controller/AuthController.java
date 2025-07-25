package com.quickboard.auth.account.controller;

import com.quickboard.auth.account.dto.AuthRequest;
import com.quickboard.auth.account.dto.AuthResponse;
import com.quickboard.auth.account.dto.AccountCreate;
import com.quickboard.auth.account.dto.AccountStatePatch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    @PostMapping("/auth/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest){
        //1. username, password 검사
        //2. 리프레스 토큰 발급해서 HttpOnlyCookie 첨부
        //3. 컨텍스트 홀더에 값 넣기(직접? or AuthenticateManager를 통해?)
        //3. accesstoken발급해서 리턴
        return null;
    }

    @PostMapping("/auth/logout")
    public void logout(){
        //1. 컨텍스트 홀더에서 회원 식별 정보(아마 userId?) 가져오기 (아마 argumentResolver에 가져오는거 하나 추가하면 될듯)
        //2. 리프레시 토큰 만료시키기(액세스는 어떻게 하지?? 프론트에서 액세스 토큰을 지우도록 해야하나? 근데 그것도 강제는 아니잖아...)
        //3. 필요는 없겠지만 컨텍스트 홀더 비우기
    }

    @PostMapping("/auth/refresh")
    public AuthResponse refreshAccessToken(@CookieValue("refreshToken") String refreshToken){
        //1. 리프레시 토큰으로 해당하는 account 검색
        //2. 유효 검사
        //3. 리프레시 토큰을 새로 발급 하고 첨부
        //4. 액세스 토큰 발급

        return null;
    }

    @PostMapping("/accounts")
    public void postAccount(@RequestBody AccountCreate accountCreate){

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
