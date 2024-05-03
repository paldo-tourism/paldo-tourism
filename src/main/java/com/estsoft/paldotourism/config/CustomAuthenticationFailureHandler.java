package com.estsoft.paldotourism.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        // 세션에 메시지 저장
        request.getSession().setAttribute("error", "이메일 또는 비밀번호를 다시 확인해 주세요.");
        // 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, "/login?error=true");
    }
}
