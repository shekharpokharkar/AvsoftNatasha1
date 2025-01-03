package com.example.avsoft.configs;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class ResourceHandlerConfig implements WebMvcConfigurer {
    @Value("${document.upload-dir}")
    private String STATIC_FILES_PATH;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public/**")
                .addResourceLocations("file:"+STATIC_FILES_PATH+"/public"+"/")
                .setCachePeriod(0);
        registry.addResourceHandler("/private/**")
                .addResourceLocations("file:" + STATIC_FILES_PATH+"/private" + "/")
                .setCachePeriod(0);
    }

}