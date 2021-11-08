package com.gh.ghdg.api.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;


@Configuration
public class CorsConfig implements WebMvcConfigurer {
//    private CorsConfiguration buildConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        //  你需要跨域的地址  注意这里的 127.0.0.1 != localhost
//        // * 表示对所有的地址都可以访问
////        corsConfiguration.addAllowedOrigin("*");
//        ArrayList<String> strings = new ArrayList<>();
//        strings.add("*");
//        corsConfiguration.setAllowedOriginPatterns(strings);
//        //  跨域的请求头
//        corsConfiguration.addAllowedHeader("*");
//        //  跨域的请求方法
//        corsConfiguration.addAllowedMethod("*");
//        //加上了这一句，大致意思是可以携带 cookie
//        //最终的结果是可以 在跨域请求的时候获取同一个 session
//        corsConfiguration.setAllowCredentials(true);
//        return corsConfiguration;
//    }
//
    /**
     * 开启跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路由
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 是否允许证书（cookies）
                .allowCredentials(true)
                // 设置允许的方法
                .allowedMethods("*")
                // 跨域允许时间
                .maxAge(3600);
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        //配置 可以访问的地址
//        source.registerCorsConfiguration("/**", buildConfig()); // 4
//        return new CorsFilter(source);
//    }
}
