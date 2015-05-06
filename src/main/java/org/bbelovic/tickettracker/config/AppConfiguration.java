package org.bbelovic.tickettracker.config;

import org.bbelovic.tickettracker.dao.UrbanTransportRideRecordDao;
import org.bbelovic.tickettracker.dao.UserDao;
import org.bbelovic.tickettracker.dao.impl.DefaultUrbanTransportRideRecordDao;
import org.bbelovic.tickettracker.dao.impl.DefaultUserDao;
import org.bbelovic.tickettracker.service.Pricelist;
import org.bbelovic.tickettracker.service.impl.DefaultPricelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
public class AppConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcOperations jdbcTemplate() {
        return new JdbcTemplate(dataSource);
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
