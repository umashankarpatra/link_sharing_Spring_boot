package com.xebia.linksharing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.filter.ForwardedHeaderFilter;

import com.xebia.linksharing.infra.config.ApplicationProperties;
import com.xebia.linksharing.service.LinkService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@EnableConfigurationProperties({ ApplicationProperties.class })
@Slf4j
public class LinkSharing {

	@Autowired
	private LinkService linkService;

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

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
