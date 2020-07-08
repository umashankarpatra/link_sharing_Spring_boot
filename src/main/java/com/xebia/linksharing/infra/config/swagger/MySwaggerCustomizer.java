package com.xebia.linksharing.infra.config.swagger;

import static springfox.documentation.builders.PathSelectors.regex;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.springframework.core.Ordered;
import org.springframework.http.ResponseEntity;

import com.xebia.linksharing.infra.config.ApplicationProperties;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * A swagger customizer to setup {@link Docket} settings.
 */
public class MySwaggerCustomizer implements SwaggerCustomizer, Ordered {

    /**
     * The default order for the customizer.
     */
    public static final int DEFAULT_ORDER = 0;

    private int order = DEFAULT_ORDER;

    private final ApplicationProperties.Swagger properties;

    public MySwaggerCustomizer(ApplicationProperties.Swagger properties) {
        this.properties = properties;
    }

    @Override
    public void customize(Docket docket) {
        Contact contact = new Contact(properties.getContactName(), properties.getContactUrl(),
                properties.getContactEmail());

        ApiInfo apiInfo = new ApiInfo(properties.getTitle(), properties.getDescription(), properties.getVersion(),
                properties.getTermsOfServiceUrl(), contact, properties.getLicense(), properties.getLicenseUrl(),
                new ArrayList<>());

        docket.host(properties.getHost()).protocols(new HashSet<>(Arrays.asList(properties.getProtocols())))
                .apiInfo(apiInfo).useDefaultResponseMessages(properties.isUseDefaultResponseMessages())
                .forCodeGeneration(true).directModelSubstitute(ByteBuffer.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class).select()
                .paths(regex(properties.getDefaultIncludePattern())).build();
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return order;
    }
}
