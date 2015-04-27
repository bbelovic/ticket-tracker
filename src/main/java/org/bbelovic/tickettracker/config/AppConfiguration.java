package org.bbelovic.tickettracker.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.bbelovic.tickettracker.dao.UrbanTransportRideRecordDao;
import org.bbelovic.tickettracker.dao.UserDao;
import org.bbelovic.tickettracker.dao.impl.DefaultUrbanTransportRideRecordDao;
import org.bbelovic.tickettracker.dao.impl.DefaultUserDao;
import org.bbelovic.tickettracker.service.Pricelist;
import org.bbelovic.tickettracker.service.impl.DefaultPricelist;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class AppConfiguration {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("testuser");
        dataSource.setPassword("test_pass");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/test");
        return dataSource;
    }

    @Bean
    public JdbcOperations jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public UrbanTransportRideRecordDao urbanTransportRideRecordDao() {
        return new DefaultUrbanTransportRideRecordDao(jdbcTemplate());
    }

    @Bean
    public Pricelist pricelist() {
        return new DefaultPricelist(jdbcTemplate());
    }

    @Bean
    public UserDao userDao() {
        return new DefaultUserDao(jdbcTemplate());
    }
}
