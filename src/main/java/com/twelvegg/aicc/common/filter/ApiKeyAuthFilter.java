package com.twelvegg.aicc.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class ApiKeyAuthFilter implements Filter {

    @Value("${fastapi.secret_key}")
    private String serverSecretKey;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // 필터 적용 경로 확인: /ai/api/v1/calls/end 또는 /ai/api/v1/customers/search
        String path = httpRequest.getRequestURI();
        boolean isProtectedPath = path.startsWith("/ai/api/v1/calls/end") || path.startsWith("/ai/api/v1/customers/search");
        
        if (!isProtectedPath) {
            chain.doFilter(request, response);
            return;
        }

        String clientKey = httpRequest.getHeader("X-API-KEY");

        // 키 검증
        if (clientKey == null || !clientKey.equals(serverSecretKey)) {
            log.warn("Invalid API Key access attempt from IP: {} for path: {}", request.getRemoteAddr(), path);
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API Key");
            return;
        }

        // [중요] S2S 호출은 Tenant 정보가 토큰에 없으므로, 기본값("default" 또는 "master")을 주입해줘야 함.
        // CustomerController에서 @RequestAttribute("tenantName")을 요구하기 때문.
        request.setAttribute("tenantName", "default");
        // 필요하다면 email, memberId도 더미로 채울 수 있음
        // request.setAttribute("email", "system@fastapi");

        chain.doFilter(request, response);
    }
}
