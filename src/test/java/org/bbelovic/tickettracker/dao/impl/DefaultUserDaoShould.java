package org.bbelovic.tickettracker.dao.impl;

import org.bbelovic.tickettracker.dao.UserDao;
import org.bbelovic.tickettracker.domain.User;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcOperations;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.bbelovic.tickettracker.dao.impl.DefaultUserDao.INSERT_NEW_USER_SQL;
import static org.mockito.Mockito.*;

public class DefaultUserDaoShould {
    private UserDao userDao;
    @Test
    public void
    store_newly_created_user_into_database() {
        JdbcOperations jdbcOperations = mock(JdbcOperations.class);
        LocalDateTime now = LocalDateTime.now();
        User user = new User(0L, "jdoe", "jdoe@company.com", "aaaa",
                now, "Europe/Prague");

        when(jdbcOperations.update(INSERT_NEW_USER_SQL, user.getUsername(), user.getPassword(),
                user.getEmail(), Timestamp.valueOf(user.getCreatedDateTime()), user.getZoneId())).thenReturn(1);

        userDao = new DefaultUserDao(jdbcOperations);
        userDao.insert(user);
        verify(jdbcOperations).update(INSERT_NEW_USER_SQL, user.getUsername(), user.getPassword(), user.getEmail(),
                Timestamp.valueOf(user.getCreatedDateTime()), user.getZoneId());
    }

    @Test
    public void
    update_existing_user_in_database() {
        JdbcOperations jdbcOperations = mock(JdbcOperations.class);
        String update = "UPDATE users SET username=?, password=?, email=?, created_date=?, zone_id=? WHERE id=?";
        LocalDateTime now = LocalDateTime.now();
        User user = new User(1L, "bbelovic", "aaaa", "bbelovic@gmail.com", now, "Europe/Prague");
        when(jdbcOperations.update(update, user.getUsername(), user.getPassword(), user.getEmail(),
                Timestamp.valueOf(user.getCreatedDateTime()), user.getZoneId(), user.getId())).thenReturn(1);
        userDao = new DefaultUserDao(jdbcOperations);
        userDao.update(user);
        verify(jdbcOperations).update(update, user.getUsername(), user.getPassword(), user.getEmail(),
                Timestamp.valueOf(user.getCreatedDateTime()), user.getZoneId(), user.getId());
    }

}
