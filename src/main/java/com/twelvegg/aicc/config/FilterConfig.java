package com.twelvegg.aicc.config;

import com.twelvegg.aicc.common.filter.JwtAuthenticationFilter;
import com.twelvegg.aicc.common.filter.ApiKeyAuthFilter;
import com.twelvegg.aicc.common.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter() {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthenticationFilter(jwtTokenProvider));
        registrationBean.addUrlPatterns("/api/*", "/ai/api/*"); // /ai/ prefix added just in case
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<ApiKeyAuthFilter> apiKeyFilter(ApiKeyAuthFilter filter) {
        FilterRegistrationBean<ApiKeyAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        // S2S 통신 경로 등록
        registrationBean.addUrlPatterns("/ai/api/v1/calls/end", "/ai/api/v1/customers/search"); 
        registrationBean.setOrder(0); // JWT 필터보다 먼저 실행
        return registrationBean;
    }
}
