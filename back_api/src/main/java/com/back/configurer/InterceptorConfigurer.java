package com.back.configurer;

import com.back.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;


@Configuration
public class InterceptorConfigurer extends WebMvcConfigurationSupport {
    @Resource
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:/templates/");

        registry.addResourceHandler("/res/**")
                .addResourceLocations("file:/data/static/back/");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**").excludePathPatterns("/", "/*.html", "/*.ico", "/assets/**", "/js/**", "/css/**", "/img/**", "/static/**", "/swagger-resources/**",
                "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/res/**").excludePathPatterns("/auth/**");
        super.addInterceptors(registry);
    }

}
