package com.xebia.linksharing.infra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Properties specific to pricing-service.
 * <p>
 * Properties are configured in the application.yml file.
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    public static final String CONTROLLER_PACKAGE = "com.xebia.linksharing.controller";
    
    public static final String SERVICE_PACKAGE = "com.xebia.linksharing.service";
    
    public static final String REPOSITORY_PACKAGE = "com.xebia.linksharing.repository";

    public static final String SLASH = "/";

    public static final String DOCS_DIR = SLASH + "docs";

    public static final String API = SLASH + "api";
    
    private final Swagger swagger = new Swagger();

    private final CorsConfiguration cors = new CorsConfiguration();

    @Getter
    @Setter
    public static class Swagger {

        private String title = ApplicationDefaults.Swagger.title;

        private String description = ApplicationDefaults.Swagger.description;

        private String version = ApplicationDefaults.Swagger.version;

        private String termsOfServiceUrl = ApplicationDefaults.Swagger.termsOfServiceUrl;

        private String contactName = ApplicationDefaults.Swagger.contactName;

        private String contactUrl = ApplicationDefaults.Swagger.contactUrl;

        private String contactEmail = ApplicationDefaults.Swagger.contactEmail;

        private String license = ApplicationDefaults.Swagger.license;

        private String licenseUrl = ApplicationDefaults.Swagger.licenseUrl;

        private String defaultIncludePattern = ApplicationDefaults.Swagger.defaultIncludePattern;

        private String host = ApplicationDefaults.Swagger.host;

        private String[] protocols = ApplicationDefaults.Swagger.protocols;

        private boolean useDefaultResponseMessages = ApplicationDefaults.Swagger.useDefaultResponseMessages;
    }
}
