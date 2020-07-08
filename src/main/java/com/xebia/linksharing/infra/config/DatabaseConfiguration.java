package com.xebia.linksharing.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(ApplicationProperties.REPOSITORY_PACKAGE)
@EnableTransactionManagement
public class DatabaseConfiguration {

}
