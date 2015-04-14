package org.bbelovic.tickettracker.dao;

import org.bbelovic.tickettracker.domain.User;

public interface UserDao {
    void insert(User user);
    void update(User user);
}
