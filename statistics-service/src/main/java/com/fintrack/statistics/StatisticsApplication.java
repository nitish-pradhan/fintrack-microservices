package com.fintrack.statistics;

import com.fintrack.statistics.repository.converter.DataPointIdReaderConverter;
import com.fintrack.statistics.repository.converter.DataPointIdWriterConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Arrays;

@SpringBootApplication
@EnableDiscoveryClient
@EnableOAuth2Client
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class StatisticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(StatisticsApplication.class, args);
	}

	@Configuration
	static class CustomConversionsConfig {

		@Bean
		public CustomConversions customConversions() {
			return new CustomConversions(Arrays.asList(new DataPointIdReaderConverter(),
					new DataPointIdWriterConverter()));
		}
	}
}
