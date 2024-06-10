package com.paymybuddy.api.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class DataSourceConfig {
	private final Dotenv dotenv;

	public DataSourceConfig(Dotenv dotenv) {
		this.dotenv = dotenv;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl(dotenv.get("DB_URL"));
		dataSource.setUsername(dotenv.get("DB_USERNAME"));
		dataSource.setPassword(dotenv.get("DB_PASSWORD"));
		return dataSource;
	}
}
