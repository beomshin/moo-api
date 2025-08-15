package com.kr.moo.interceptor;

import com.kr.moo.exception.GlobalErrorCode;
import com.kr.moo.exception.MooGlobalException;
import com.kr.moo.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtHandshakeInterceptorImpl implements JwtHandshakeInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            String authorization = servletRequest.getServletRequest().getHeader("Authorization");

            if (authorization != null && authorization.startsWith("Bearer ")) {
                String token = jwtProvider.resolveToken(authorization);

                try {
                    boolean isValidToken = jwtProvider.validateToken(token);

                    if (!isValidToken) {
                        throw new MooGlobalException(GlobalErrorCode.JWT_TOKEN_ERROR);
                    }

                    attributes.put("storeId", jwtProvider.getStoreId(token));
                    attributes.put("userId", jwtProvider.getUserId(token));

                    return true;
                } catch (MooGlobalException e) {
                    throw e;
                } catch (Exception e) {
                    log.error("◆ 좌석 조회 JWT 토큰 인증 오류 발생 : ", e);
                    throw new MooGlobalException(GlobalErrorCode.JWT_TOKEN_ERROR);
                }
            }

        }

        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }

}
