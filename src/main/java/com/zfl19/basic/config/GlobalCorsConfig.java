package com.zfl19.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author: 19zfl
 * @description: 前端跨域访问配置类
 * @date 2023-12-03
 */
@Configuration
public class GlobalCorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 1. 添加允许访问的ip
        config.addAllowedOrigin("http://127.0.0.1:6001");
        config.addAllowedOrigin("http://localhost:6001");
        // 2. 配置是否发送cookie信息
        config.setAllowCredentials(true);
        // 3. 配置前端允许请求的方式
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        // 4. 允许请求头信息
        config.addAllowedHeader("*");
        // 5. 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", config);
        // 6. 返回
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
