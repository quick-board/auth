package com.quickboard.auth.account.controller;

import com.quickboard.auth.account.dto.AuthRequest;
import com.quickboard.auth.account.dto.AuthResponse;
import com.quickboard.auth.account.entity.Account;
import com.quickboard.auth.account.service.AuthService;
import com.quickboard.auth.account.utils.JwtManager;
import com.quickboard.auth.common.argumentresolver.annotations.CurrentUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtManager jwtManager;
    private static final String REFRESH_COOKIE_NAME = "refresh_token";

    //todo 일반 컨트롤러말고 필터로 로그인 옮기는거 고민하기 https://www.devyummi.com/page?id=668d55e4ceede2499082fc28
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest){
        Account account = authService.authenticateAndIssueRefreshToken(authRequest.username(), authRequest.password());
        String accessToken = jwtManager.generateAccessToken(account);
        ResponseCookie cookie = generateCookie(account.getRefreshToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new AuthResponse(accessToken));
    }



    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CurrentUserId Long userId){
        //todo 블랙리스트만들어서 만료된 액세스 토큰들 추가하기?

        Optional<Long> optionalUserId = Optional.ofNullable(userId);
        optionalUserId.ifPresent(authService::expireRefreshToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, emptyCookie().toString())
                .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshAccessToken(@CookieValue("refresh_token") String refreshToken){
        Account account = authService.rotateRefreshTokenIfValid(refreshToken);
        String accessToken = jwtManager.generateAccessToken(account);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, account.getRefreshToken())
                .body(new AuthResponse(accessToken));
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

    private static ResponseCookie emptyCookie(){
        return ResponseCookie.from(REFRESH_COOKIE_NAME)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
    }
}
