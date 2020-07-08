package com.xebia.linksharing.infra.config;

public interface ApplicationDefaults {

    public int DEFAULT_PAGE_SIZE = 12;

    interface Swagger {

        String title = "Application API";

        String description = "API documentation";

        String version = "0.0.1";

        String termsOfServiceUrl = null;

        String contactName = null;

        String contactUrl = null;

        String contactEmail = null;

        String license = null;

        String licenseUrl = null;

        String defaultIncludePattern = "/api/.*";

        String host = null;

        String[] protocols = {};

        boolean useDefaultResponseMessages = true;
    }
}
