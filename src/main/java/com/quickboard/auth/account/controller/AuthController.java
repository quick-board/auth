package com.quickboard.auth.account.controller;

import com.quickboard.auth.account.dto.AuthRequest;
import com.quickboard.auth.account.dto.AuthResponse;
import com.quickboard.auth.account.entity.Account;
import com.quickboard.auth.account.service.AuthService;
import com.quickboard.auth.account.utils.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;
    private final JwtManager jwtManager;
    private static final String REFRESH_COOKIE_NAME = "refresh_token";

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest){
        Account account = authService.authenticateAndIssueRefreshToken(authRequest.username(), authRequest.password());
        String accessToken = jwtManager.generateAccessToken(account);
        ResponseCookie cookie = generateCookie(account.getRefreshToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new AuthResponse(accessToken));
    }



    @PostMapping("/auth/logout")
    public void logout(){
        //1. 컨텍스트 홀더에서 회원 식별 정보(아마 userId?) 가져오기 (아마 argumentResolver에 가져오는거 하나 추가하면 될듯)
        //2. 리프레시 토큰 만료시키기(액세스는 어떻게 하지?? 프론트에서 액세스 토큰을 지우도록 해야하나? 근데 그것도 강제는 아니잖아...)
        //3. 필요는 없겠지만 컨텍스트 홀더 비우기

        //todo 필터 만들기
    }

    @PostMapping("/auth/refresh")
    public AuthResponse refreshAccessToken(@CookieValue("refreshToken") String refreshToken){
        //1. 리프레시 토큰으로 해당하는 account 검색
        //2. 유효 검사
        //3. 리프레시 토큰을 새로 발급 하고 첨부
        //4. 액세스 토큰 발급

        return null;
    }

    private static ResponseCookie generateCookie(String refreshToken) {
        return ResponseCookie.from(REFRESH_COOKIE_NAME, refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();
    }
}
