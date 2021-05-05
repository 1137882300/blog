package com.zhong.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by cc on 2021/5/2
 */
@Configuration
public class FileConfigurer implements WebMvcConfigurer {

    /**
     * 资源映射路径
     * addResourceHandler：访问映射路径
     * addResourceLocations：资源绝对路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/uploads/**")
                .addResourceLocations("file:D:\\ideaProject\\blog\\src\\main\\resources\\static\\uploads\\");
    }
}
