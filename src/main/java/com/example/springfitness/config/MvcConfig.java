package com.example.springfitness.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//Защита
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/login").setViewName("login");
        }
}
