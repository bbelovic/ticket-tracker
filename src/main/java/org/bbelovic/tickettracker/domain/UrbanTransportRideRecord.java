package org.bbelovic.tickettracker.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import static java.lang.String.format;

public class UrbanTransportRideRecord {
    private final long id;
    private final LocalDateTime rideDateTime;
    private final BigDecimal price;
    private final TicketType ticketType;
    private final long userId;

    public UrbanTransportRideRecord(long id, LocalDateTime rideDateTime,
                                    BigDecimal price, TicketType ticketType, long userId) {
        this.id = Objects.requireNonNull(id);
        this.rideDateTime = Objects.requireNonNull(rideDateTime);
        this.price = Objects.requireNonNull(price);
        this.ticketType = Objects.requireNonNull(ticketType);
        this.userId = Objects.requireNonNull(userId);
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getRideDateTime() {
        return rideDateTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrbanTransportRideRecord that = (UrbanTransportRideRecord) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(rideDateTime, that.rideDateTime) &&
                Objects.equals(price, that.price) &&
                Objects.equals(ticketType, that.ticketType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rideDateTime, price, ticketType, userId);
    }

    @Override
    public String toString() {
        return format("UrbanTransportRideRecord[id=%d, rideDateTime=%s, price=%s, ticketType=%s, userId=%d]"
                ,id, rideDateTime, price, ticketType, userId);
    }
}
