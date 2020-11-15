package com.nasserBoukehil.worldGdp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DBConfiguration {
	@Value("${jdbcUrl}") String jdbcUrl;
	@Value("${dataSource.user}") String username;
	@Value("${dataSource.password}") String password;
	@Value("${dataSourceClassName}") String className;
	
	@Bean
	public DataSource getDataSource() {
		HikariDataSource aDataSource = new HikariDataSource();
		aDataSource.setJdbcUrl(jdbcUrl);
		aDataSource.setUsername(username);
		aDataSource.setPassword(password);
		aDataSource.setDriverClassName(className);
		return aDataSource;
	}
	
	@Bean
	public NamedParameterJdbcTemplate namedParamJdbcTemplate() {
		NamedParameterJdbcTemplate aNJdbcTemplate =
				new NamedParameterJdbcTemplate(getDataSource());
		return aNJdbcTemplate;
	}

}
