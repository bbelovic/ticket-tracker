package org.bbelovic.tickettracker.dao;

import org.bbelovic.tickettracker.domain.TicketType;
import org.bbelovic.tickettracker.domain.UrbanTransportRideRecord;

import java.time.LocalDateTime;
import java.util.Collection;

public interface UrbanTransportRideRecordDao {
    Collection<UrbanTransportRideRecord> findAll();
    Collection<UrbanTransportRideRecord> findAll(TicketType ticketType);
    Collection<UrbanTransportRideRecord> findAll(LocalDateTime localDateTime);

    void insert(UrbanTransportRideRecord rideRecord);
}
