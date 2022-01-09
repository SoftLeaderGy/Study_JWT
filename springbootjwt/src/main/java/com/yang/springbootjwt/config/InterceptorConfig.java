package com.yang.springbootjwt.config;

import com.yang.springbootjwt.interceptors.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description:
 * @Author: Guo.Yang
 * @Date: 2022/01/09/23:06
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                .addPathPatterns("/user/test") // 拦截业务接口
                .excludePathPatterns("/user/login"); // 放行登陆生成token接口
    }
}
