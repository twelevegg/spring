package com.twelvegg.aicc.config;

import com.twelvegg.aicc.common.filter.JwtAuthenticationFilter;
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
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
