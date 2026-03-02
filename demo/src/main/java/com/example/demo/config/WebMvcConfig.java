package com.example.demo.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /** Serves uploaded product images from the same directory ProductService uses. */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadPath = Paths.get("static/images").toAbsolutePath().normalize();
        String location = "file:" + uploadPath + "/";
        registry.addResourceHandler("/images/**")
                .addResourceLocations(location);
    }
}
