package com.optum.linksharing.infra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Properties specific to pricing-service.
 * <p>
 * Properties are configured in the application.yml file.
 */

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

	public static final String CONTROLLER_PACKAGE = "com.optum.linksharing.controller";

	public static final String SERVICE_PACKAGE = "com.optum.linksharing.service";

	public static final String REPOSITORY_PACKAGE = "com.optum.linksharing.repository";

	public static final String SLASH = "/";

	public static final String DOCS_DIR = SLASH + "docs";

	public static final String API = SLASH + "api";

	private final Swagger swagger = new Swagger();

	private final CorsConfiguration cors = new CorsConfiguration();

	public Swagger getSwagger() {
		return swagger;
	}

	public CorsConfiguration getCors() {
		return cors;
	}

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

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getTermsOfServiceUrl() {
			return termsOfServiceUrl;
		}

		public void setTermsOfServiceUrl(String termsOfServiceUrl) {
			this.termsOfServiceUrl = termsOfServiceUrl;
		}

		public String getContactName() {
			return contactName;
		}

		public void setContactName(String contactName) {
			this.contactName = contactName;
		}

		public String getContactUrl() {
			return contactUrl;
		}

		public void setContactUrl(String contactUrl) {
			this.contactUrl = contactUrl;
		}

		public String getContactEmail() {
			return contactEmail;
		}

		public void setContactEmail(String contactEmail) {
			this.contactEmail = contactEmail;
		}

		public String getLicense() {
			return license;
		}

		public void setLicense(String license) {
			this.license = license;
		}

		public String getLicenseUrl() {
			return licenseUrl;
		}

		public void setLicenseUrl(String licenseUrl) {
			this.licenseUrl = licenseUrl;
		}

		public String getDefaultIncludePattern() {
			return defaultIncludePattern;
		}

		public void setDefaultIncludePattern(String defaultIncludePattern) {
			this.defaultIncludePattern = defaultIncludePattern;
		}

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public String[] getProtocols() {
			return protocols;
		}

		public void setProtocols(String[] protocols) {
			this.protocols = protocols;
		}

		public boolean isUseDefaultResponseMessages() {
			return useDefaultResponseMessages;
		}

		public void setUseDefaultResponseMessages(boolean useDefaultResponseMessages) {
			this.useDefaultResponseMessages = useDefaultResponseMessages;
		}
	}
}
