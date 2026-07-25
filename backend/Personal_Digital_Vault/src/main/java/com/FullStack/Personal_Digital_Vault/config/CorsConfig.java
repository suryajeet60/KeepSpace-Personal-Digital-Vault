package com.FullStack.Personal_Digital_Vault.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig
{

    @Bean
    public WebMvcConfigurer corsConfigurer()
    {

        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry)
            {

                registry.addMapping("/**")

                        // React frontend URL
                        .allowedOrigins("http://localhost:5173")

                        // Allowed HTTP methods
                        .allowedMethods(
                                "GET",
                                "POST",
                                "PUT",
                                "DELETE",
                                "PATCH",
                                "OPTIONS"
                        )

                        // Allowed headers
                        .allowedHeaders("*")

                        // Allow credentials
                        .allowCredentials(true)

                        // Cache duration
                        .maxAge(3600);
            }
        };
    }
}
