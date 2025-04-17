package com.group8.foodwizard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

/**
 * Configuration class for serving a Single Page Application (SPA) such as a
 * React frontend from the Spring Boot backend. Ensures proper routing of static
 * files and fallback to index.html for SPA client-side routing.
 */
@Configuration
public class SpaWebMvcConfigurer implements WebMvcConfigurer {

    /**
     * Configures how static resources are served.
     * Maps all requests to the `/static` directory and ensures that SPA routes
     * fallback to serving `index.html` instead of returning 404 errors.
     *
     * @param registry the resource handler registry to configure
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Ensure static assets from React build (js, css, images) are served
        registry.addResourceHandler("/**") // Match everything
                .addResourceLocations("classpath:/static/") // Location of your React build output
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        // Try to find the actual resource (e.g., main.js, style.css)
                        Resource requestedResource = location.createRelative(resourcePath);
                        // If the resource exists and is accessible (is not a directory, etc.),
                        // or if it's an API endpoint (you might adjust this check), serve it directly.
                        // Otherwise, fall back to serving index.html
                        if (requestedResource.exists() && requestedResource.isReadable()
                                || isApiRequest(resourcePath)) {
                            return requestedResource;
                        } else {
                            // Serve index.html for non-file, non-api paths (like /recipe/53051)
                            return new ClassPathResource("/static/index.html");
                        }
                    }
                });
    }

    /**
     * Determines if a given path is an API request.
     * Prevents API routes from being handled by the SPA fallback logic.
     *
     * @param path the requested path
     * @return true if the path is an API call, false otherwise
     */
    // Helper method to avoid forwarding API calls to index.html
    // Adjust the pattern based on your API paths
    private boolean isApiRequest(String path) {
        return path.startsWith("/api/") || path.startsWith("/actuator/"); // Add other API prefixes if needed
    }

    // Optional: If you ONLY want to handle specific frontend routes like /recipe/**
    // You could use addViewControllers instead or in combination, but the
    // resource resolver approach above is generally more robust for SPAs.
    /*
     * @Override
     * public void addViewControllers(ViewControllerRegistry registry) {
     * // Forward requests like /recipe/*, /user/* etc. to index.html
     * // Note: This requires more specific path patterns.
     * registry.addViewController("/recipe/**").setViewName("forward:/index.html");
     * registry.addViewController("/other-spa-route/**").setViewName(
     * "forward:/index.html");
     * // Add more patterns as needed for your SPA routes
     * }
     */
}
