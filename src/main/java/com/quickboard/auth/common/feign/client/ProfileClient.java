package com.quickboard.auth.common.feign.client;

import com.quickboard.auth.common.feign.dto.ProfileOriginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "resource-profile")
public interface ProfileClient {

    @GetMapping("/rsc/v1/profiles/{id}")
    ProfileOriginResponse getProfile(@PathVariable("id") Long profileId);

    @GetMapping("/rsc/v1/profiles/profile")
    ProfileOriginResponse getProfileByAccountId(@RequestParam("account-id")Long accountId,
                                                @RequestHeader("passport") String serializedPassport);

}
