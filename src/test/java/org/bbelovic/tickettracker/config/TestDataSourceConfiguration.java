package org.bbelovic.tickettracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;

@Profile("development")
@Configuration
public class TestDataSourceConfiguration {
    @Bean
    public DataSource dataSource() {
        return new SingleConnectionDataSource();
    }
}
