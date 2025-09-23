package com.quickboard.auth.account.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quickboard.auth.account.dto.AuthRequest;
import com.quickboard.auth.account.dto.AuthResponse;
import com.quickboard.auth.account.entity.Account;
import com.quickboard.auth.account.service.AuthService;
import com.quickboard.auth.account.utils.JwtManager;
import com.quickboard.auth.common.argumentresolver.annotations.CurrentUserId;
import com.quickboard.auth.common.argumentresolver.annotations.ServicePassport;
import com.quickboard.auth.common.feign.client.ProfileClient;
import com.quickboard.auth.common.feign.dto.Passport;
import com.quickboard.auth.common.feign.dto.ProfileOriginResponse;
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
    private final ProfileClient profileClient;
    private final ObjectMapper objectMapper;
    private static final String REFRESH_COOKIE_NAME = "refresh_token";

    //todo 일반 컨트롤러말고 필터로 로그인 옮기는거 고민하기 https://www.devyummi.com/page?id=668d55e4ceede2499082fc28
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest,
                                              @ServicePassport Passport passport) throws JsonProcessingException {
        Account account = authService.authenticateAndIssueRefreshToken(authRequest.username(), authRequest.password());
        String serializedPassport = objectMapper.writeValueAsString(passport);

        ProfileOriginResponse profile = profileClient.getProfileByAccountId(account.getId(), serializedPassport);
        String accessToken = jwtManager.generateAccessToken(account, profile);
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
    public ResponseEntity<AuthResponse> refreshAccessToken(@CookieValue(REFRESH_COOKIE_NAME) String refreshToken,
                                                           @ServicePassport Passport passport) throws JsonProcessingException {
        Account account = authService.rotateRefreshTokenIfValid(refreshToken);
        String serializedPassport = objectMapper.writeValueAsString(passport);

        ProfileOriginResponse profile = profileClient.getProfileByAccountId(account.getId(), serializedPassport);
        String accessToken = jwtManager.generateAccessToken(account, profile);

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
