package org.bbelovic.tickettracker.dao.impl;

import org.bbelovic.tickettracker.dao.UserDao;
import org.bbelovic.tickettracker.domain.User;
import org.springframework.jdbc.core.JdbcOperations;

import java.sql.Timestamp;

public class DefaultUserDao implements UserDao {
    static final String INSERT_NEW_USER_SQL = "INSERT INTO users (username, password, email, created_date, zone_id) " +
            "VALUES (?, ?, ?, ?, ?)";
    private final JdbcOperations jdbcOperations;

    public DefaultUserDao(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public void insert(User user) {
        jdbcOperations.update(INSERT_NEW_USER_SQL,
                user.getUsername(), user.getPassword(), user.getEmail(),
                Timestamp.valueOf(user.getCreatedDateTime()), user.getZoneId());
    }

    @Override
    public void update(User user) {
        jdbcOperations.update("UPDATE users SET username=?, password=?, email=?, created_date=?, zone_id=? WHERE id=?",
                user.getUsername(), user.getPassword(), user.getEmail(), Timestamp.valueOf(user.getCreatedDateTime()),
                user.getZoneId(), user.getId());
    }
}
