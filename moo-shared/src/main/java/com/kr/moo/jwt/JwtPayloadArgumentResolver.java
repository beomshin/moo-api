package com.kr.moo.jwt;

import com.kr.moo.util.JwtProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtPayloadArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JwtPayload.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request= (HttpServletRequest) webRequest.getNativeRequest();

        String token = jwtProvider.resolveToken(request.getHeader("Authorization"));
        if (token == null) return null;

        Claims claims = jwtProvider.parseToken(token); // JWT 파싱
        JwtPayload annotation = parameter.getParameterAnnotation(JwtPayload.class);
        String key = annotation.value();

        return claims.get(key);
    }

}
