package org.bbelovic.tickettracker.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.lang.String.format;

public class User {
    private final long id;
    private final String username;
    private final String email;
    private final String password;
    private final LocalDateTime createdDateTime;
    private final String zoneId;

    public User(long id, String username, String password, String email,
                LocalDateTime createdDateTime, String zoneId) {
        this.id = Objects.requireNonNull(id);
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
        this.email = Objects.requireNonNull(email);
        this.createdDateTime = Objects.requireNonNull(createdDateTime);
        this.zoneId = Objects.requireNonNull(zoneId);
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public long getId() {
        return id;
    }

    public String getZoneId() {
        return zoneId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(createdDateTime, user.createdDateTime) &&
                Objects.equals(zoneId, user.zoneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, createdDateTime, zoneId);
    }

    @Override
    public String toString() {
        return format("User[id=%d, username='%s', email='%s', password='%s', createdDateTime=%s, zoneId='%s']"
                ,id, username, email, password, createdDateTime, zoneId);
    }
}
