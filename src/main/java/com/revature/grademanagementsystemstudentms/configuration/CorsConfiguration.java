package com.revature.grademanagementsystemstudentms.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer
{
     @Override
        public void addCorsMappings(CorsRegistry registry) {

            registry.addMapping("/**")
                    .allowedOrigins(
                            "https://gradingapp-43d72.firebaseapp.com")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD")
                    .allowCredentials(true)
            ;
        }
}
