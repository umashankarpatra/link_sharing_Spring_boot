package com.optum.linksharing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.filter.ForwardedHeaderFilter;

import com.optum.linksharing.infra.config.ApplicationProperties;
import com.optum.linksharing.service.LinkService;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@EnableConfigurationProperties({ ApplicationProperties.class })
public class LinkSharing {

	@Autowired
	private LinkService linkService;

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	private Logger log = LoggerFactory.getLogger(LinkSharing.class);

	public static void main(String[] args) {
		SpringApplication.run(LinkSharing.class, args);
	}

	@Bean
	ForwardedHeaderFilter forwardedHeaderFilter() {
		return new ForwardedHeaderFilter();
	}

	@Scheduled(cron = "0 0 12 * * ?")
	public void deactiveOldLinkJob() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -5);
		log.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
		linkService.deactiveLinksTask(cal.getTime());

	}
}
