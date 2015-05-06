package org.bbelovic.tickettracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Profile("production")
@Configuration
public class DataSourceConfiguration {
    @Bean
    public DataSource dataSource() {
        try {
            InitialContext initialContext = new InitialContext();
            Context env = (Context) initialContext.lookup("java:comp/env");
            return (DataSource) env.lookup("jdbc/PostgreSQLDS");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

    }

}
