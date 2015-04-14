package org.bbelovic.tickettracker.dao.impl;


import org.bbelovic.tickettracker.dao.UrbanTransportRideRecordDao;
import org.bbelovic.tickettracker.domain.UrbanTransportRideRecord;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.util.Arrays.asList;
import static org.bbelovic.tickettracker.dao.impl.DefaultUrbanTransportRideRecordDao.INSERT_RIDE_RECORD;
import static org.bbelovic.tickettracker.dao.impl.DefaultUrbanTransportRideRecordDao.SELECT_ALL_RIDE_RECORDS;
import static org.bbelovic.tickettracker.domain.TicketType.SMS_20;
import static org.bbelovic.tickettracker.domain.TicketType.SMS_75;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class DefaultUrbanTransportRideRecordDaoShould {

    @Test
    public void
    insert_new_ride_record_into_database() {
        LocalDateTime rideDateTime = LocalDateTime.now();
        UrbanTransportRideRecord rideRecord = new UrbanTransportRideRecord(1L, rideDateTime, TEN, SMS_75, 1L);
        JdbcOperations jdbcOperations = mock(JdbcOperations.class);
        when(jdbcOperations.update(INSERT_RIDE_RECORD,
                Timestamp.valueOf(rideRecord.getRideDateTime()), rideRecord.getTicketType().name(), rideRecord.getUserId())).thenReturn(1);
        UrbanTransportRideRecordDao rideRecordDao = new DefaultUrbanTransportRideRecordDao(jdbcOperations);
        rideRecordDao.insert(rideRecord);
        verify(jdbcOperations).update(INSERT_RIDE_RECORD,
                Timestamp.valueOf(rideRecord.getRideDateTime()), rideRecord.getTicketType().name(), rideRecord.getUserId());
    }

    @Test
    public void
    return_all_transport_ride_records() {
        JdbcOperations jdbcOperations = mock(JdbcOperations.class);
        LocalDateTime now = LocalDateTime.now();
        when(jdbcOperations.query(eq(SELECT_ALL_RIDE_RECORDS), any(RowMapper.class)))
                .thenReturn(asList(new UrbanTransportRideRecord(1L, now, ONE, SMS_20, 10L)));
        UrbanTransportRideRecordDao rideRecordDao = new DefaultUrbanTransportRideRecordDao(jdbcOperations);
        Collection<UrbanTransportRideRecord> actual = rideRecordDao.findAll();
        assertEquals(asList(new UrbanTransportRideRecord(1L, now, ONE, SMS_20, 10L)), actual);
        verify(jdbcOperations).query(eq(SELECT_ALL_RIDE_RECORDS), any(RowMapper.class));
    }
}
