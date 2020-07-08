package com.xebia.linksharing.infra.config.swagger;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Register Springfox plugins.
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnBean(Docket.class)
@AutoConfigureAfter(SwaggerAutoConfiguration.class)
public class SwaggerPluginsAutoConfiguration {

    @Configuration
    @ConditionalOnClass(Pageable.class)
    public static class SpringPagePluginConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public PageableParameterBuilderPlugin pageableParameterBuilderPlugin(TypeNameExtractor typeNameExtractor,
                TypeResolver typeResolver) {
            return new PageableParameterBuilderPlugin(typeNameExtractor, typeResolver);
        }
    }
}
