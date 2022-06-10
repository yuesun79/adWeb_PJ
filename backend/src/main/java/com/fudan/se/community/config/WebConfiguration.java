package com.fudan.se.community.config;

import com.fudan.se.community.interceptor.TokenInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.config.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 */
@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {
  /*  @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }*/
    @Autowired
    private TokenInterceptor tokenInterceptor ;


    /**
     * 解决跨域请求
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOriginPatterns("*")
                .allowCredentials(true);
    }

    /**
     * 异步请求配置
     * @param configurer
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(3)));
        configurer.setDefaultTimeout(30000);
    }

    /**
     * 配置拦截器、拦截路径
     * 每次请求到拦截的路径，就回去执行拦截器中的方法
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = new ArrayList<>();

        // 排除拦截，除了注册登录（此时还没TOKEN）,其他都拦截
        excludePath.add("/api/register"); // 登录
        excludePath.add("/api/login/**"); // 注册
        excludePath.add("/static/**"); // 把静态资源的访问也排除
        excludePath.add("/assets/**"); // 把静态资源的访问也排除

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**") // 添加拦截的路径模式 /** 代表全部拦截
                .excludePathPatterns(excludePath);
        System.out.println("add Interceptor");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}

