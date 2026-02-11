package com.twelvegg.aicc.config;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.Set;

@Configuration
public class CorsConfig {

    // 로컬 + 운영(도메인) 허용
    private static final Set<String> ALLOWED_ORIGINS = Set.of(
            "http://localhost:5173",
            "https://www.csnavigator.cloud",
            "https://csnavigator.cloud"
    );

    @Bean
    public FilterRegistrationBean<Filter> corsFilter() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();

        bean.setFilter((request, response, chain) -> {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            String origin = req.getHeader("Origin");

            // ✅ Origin이 허용 목록에 있으면 CORS 헤더 세팅
            if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
                res.setHeader("Access-Control-Allow-Origin", origin);
                res.setHeader("Vary", "Origin");
                res.setHeader("Access-Control-Allow-Credentials", "true");
                res.setHeader("Access-Control-Allow-Headers", "authorization, content-type");
                res.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,PATCH,OPTIONS");
            }

            // ✅ Preflight 요청은 여기서 바로 OK 응답
            if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
                res.setStatus(HttpServletResponse.SC_OK);
                return;
            }

            // ✅ 다음 필터/컨트롤러로 요청 전달
            chain.doFilter(request, response);
        });

        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
