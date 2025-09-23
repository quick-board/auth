package com.quickboard.auth.common.feign.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.quickboard.auth.account.enums.AccountState;
import com.quickboard.auth.account.enums.Role;
import com.quickboard.auth.common.feign.enums.CallerType;


@JsonIgnoreProperties(ignoreUnknown = true)
public record Passport(
        CallerType callerType,
        AnonymousDetails anonymousDetails,
        EndUserDetails endUserDetails,
        ServiceDetails serviceDetails
) {
    public static Passport anonymousPassport(String guestId, String ip){
        return new Passport(
                CallerType.ANONYMOUS,
                new AnonymousDetails(guestId, ip),
                null,
                null
        );
    }

    public static Passport endUserPassport(Long accountId, AccountState accountState, Role role, Long profileId, String nickname){
        return new Passport(
                CallerType.END_USER,
                null,
                new EndUserDetails(accountId, accountState, role, profileId, nickname),
                null
        );
    }

    public static Passport servicePassport(String serviceName, String clientRequestPath){
        return new Passport(
                CallerType.SERVICE,
                null,
                null,
                new ServiceDetails(serviceName, clientRequestPath)
        );
    }

}
