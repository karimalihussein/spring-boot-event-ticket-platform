package com.personal.tickets.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // Serve static assets from the frontend build
        // This handler has lower priority than controller mappings (controllers are matched first)
        // Only handles requests that don't match API routes
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(@NonNull String resourcePath, @NonNull Resource location) throws IOException {
                        // Explicitly exclude API routes - these should be handled by controllers
                        // If controller doesn't exist, NoHandlerFoundException will be thrown
                        if (resourcePath.startsWith("api/") || resourcePath.startsWith("/api/")) {
                            return null;
                        }
                        
                        Resource requestedResource = location.createRelative(resourcePath);
                        
                        // If the requested static resource exists, serve it
                        if (requestedResource.exists() && requestedResource.isReadable()) {
                            return requestedResource;
                        }
                        
                        // For SPA routes (all non-API routes), serve index.html
                        // This allows React Router to handle client-side routing
                        return new ClassPathResource("/static/index.html");
                    }
                });
        // Note: Controllers have higher priority by default in Spring MVC
        // Resource handlers are matched after controller mappings fail
    }
}

