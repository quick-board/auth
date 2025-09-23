package com.quickboard.auth.common.argumentresolver.resolver;

import com.quickboard.auth.common.argumentresolver.annotations.ServicePassport;
import com.quickboard.auth.common.feign.dto.Passport;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class ServicePassportResolver implements HandlerMethodArgumentResolver {

    private final String serviceName;

    public ServicePassportResolver(@Value("${spring.application.name}") String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ServicePassport.class) &&
                parameter.getParameterType().equals(Passport.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String servletPath = request.getServletPath();
        return Passport.servicePassport(serviceName, servletPath);
    }
}
