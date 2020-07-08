package com.xebia.linksharing.infra.config.web;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.xebia.linksharing.infra.config.ApplicationProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
@Slf4j
public class WebConfigurer
        implements ServletContextInitializer, WebServerFactoryCustomizer<WebServerFactory>, WebMvcConfigurer {

    private final Environment env;

    private final ApplicationProperties applicationProperties;

    public WebConfigurer(final Environment env, final ApplicationProperties applicationProperties) {
        this.env = env;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        if (env.getActiveProfiles().length != 0) {
            log.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
        }
        log.info("Web application fully configured");
    }

    /**
     * Customize the Servlet engine: Mime types, the document root, the cache.
     */
    @Override
    public void customize(final WebServerFactory server) {
        setMimeMappings(server);
    }

    private void setMimeMappings(final WebServerFactory server) {
        if (server instanceof ConfigurableServletWebServerFactory) {
            MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
            mappings.add("html", MediaType.TEXT_HTML_VALUE + ";charset=" + StandardCharsets.UTF_8.name().toLowerCase());
            mappings.add("json", MediaType.TEXT_HTML_VALUE + ";charset=" + StandardCharsets.UTF_8.name().toLowerCase());
            ConfigurableServletWebServerFactory servletWebServer = (ConfigurableServletWebServerFactory) server;
            servletWebServer.setMimeMappings(mappings);
        }
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = applicationProperties.getCors();
        if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
            log.debug("Registering CORS filter");
            source.registerCorsConfiguration("/api/**", config);
            source.registerCorsConfiguration("/v2/api-docs", config);
            source.registerCorsConfiguration("/management/**", config);
        }

        return new CorsFilter(source);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        // Add any custom method argument resolvers
    }

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        // Add any custom formatters
    }
}
