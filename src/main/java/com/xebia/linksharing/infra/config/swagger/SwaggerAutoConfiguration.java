package com.xebia.linksharing.infra.config.swagger;

import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.Servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.http.ResponseEntity;
import org.springframework.plugin.core.SimplePluginRegistry;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.DispatcherServlet;

import com.fasterxml.classmate.TypeResolver;
import com.xebia.linksharing.infra.config.ApplicationProperties;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Springfox Swagger configuration.
 * <p>
 * Warning! When having a lot of REST endpoints, Springfox can become a
 * performance issue. In that case, you can skip swagger by not including
 * swagger Spring profile in dev/prod application yml, so that this bean is
 * ignored.
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({ ApiInfo.class, BeanValidatorPluginsConfiguration.class, Servlet.class, DispatcherServlet.class })
@AutoConfigureAfter(ApplicationProperties.class)
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerAutoConfiguration {

	static final String STARTING_MESSAGE = "Starting Swagger";

	static final String STARTED_MESSAGE = "Started Swagger in {} ms";

	private final Logger log = LoggerFactory.getLogger(SwaggerAutoConfiguration.class);

	private final ApplicationProperties.Swagger properties;

	@Autowired
	private TypeResolver typeResolver;

	public SwaggerAutoConfiguration(ApplicationProperties applicationProperties) {
		this.properties = applicationProperties.getSwagger();
	}

	/**
	 * Springfox configuration for the API Swagger docs.
	 *
	 * @param swaggerCustomizers Swagger customizers
	 * @param alternateTypeRules alternate type rules
	 * @return the Swagger Springfox configuration
	 */
	@Bean
	@ConditionalOnMissingBean(name = "swaggerSpringfoxApiDocket")
	public Docket swaggerSpringfoxApiDocket(List<SwaggerCustomizer> swaggerCustomizers,
			ObjectProvider<AlternateTypeRule[]> alternateTypeRules) {
		log.debug(STARTING_MESSAGE);
		StopWatch watch = new StopWatch();
		watch.start();

		Docket docket = createDocket();
		// Apply all SwaggerCustomizers orderly.
		swaggerCustomizers.forEach(customizer -> customizer.customize(docket));

		// Add all AlternateTypeRules if available in spring bean factory.
		// Also you can add your rules in a customizer bean above.
		Optional.ofNullable(alternateTypeRules.getIfAvailable()).ifPresent(docket::alternateTypeRules);

		watch.stop();
		log.debug(STARTED_MESSAGE, watch.getTotalTimeMillis());
		return docket;
	}

	/**
	 * Pricing Swagger Customizer
	 *
	 * @return the Swagger Customizer of Pricing
	 */
	@Bean
	public MySwaggerCustomizer mySwaggerCustomizer() {
		return new MySwaggerCustomizer(properties);
	}

	public Docket createDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.alternateTypeRules(newRule(
						typeResolver.resolve(DeferredResult.class,
								typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
						typeResolver.resolve(WildcardType.class)))
				.select().apis(RequestHandlerSelectors.basePackage(ApplicationProperties.CONTROLLER_PACKAGE))
				.paths(regex(ApplicationProperties.API + ".*")).build();
	}

	@Primary
	@Bean
	public LinkDiscoverers discoverers() {
		List<LinkDiscoverer> plugins = new ArrayList<>();
		plugins.add(new CollectionJsonLinkDiscoverer());
		return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
	}
}
