package com.cs.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.cs.repository")
@EnableTransactionManagement
public class DatabaseConfiguration {

	@Bean(destroyMethod = "close")
	@ConfigurationProperties(prefix = "spring.datasource.dbadmin")
	public DataSource adminDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(destroyMethod = "close")
	@ConfigurationProperties(prefix = "spring.datasource.dbuser")
	public DataSource userDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@Primary
	public DataSource dataSource() {
		RoutingDataSource routingDataSource = new RoutingDataSource();

		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DbType.dbadmin, adminDataSource());
		targetDataSources.put(DbType.dbuser, userDataSource());

		routingDataSource.setTargetDataSources(targetDataSources);
		routingDataSource.afterPropertiesSet();
		routingDataSource.setDefaultTargetDataSource(userDataSource());

		return routingDataSource;
	}
}
