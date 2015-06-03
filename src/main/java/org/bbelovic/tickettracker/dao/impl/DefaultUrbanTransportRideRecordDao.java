package org.bbelovic.tickettracker.dao.impl;

import org.bbelovic.tickettracker.dao.UrbanTransportRideRecordDao;
import org.bbelovic.tickettracker.domain.TicketType;
import org.bbelovic.tickettracker.domain.UrbanTransportRideRecord;
import org.springframework.jdbc.core.JdbcOperations;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

public class DefaultUrbanTransportRideRecordDao implements UrbanTransportRideRecordDao {

    static final String SELECT_ALL_RIDE_RECORDS = "SELECT urban_transport_ride_records.*, pricelist.pricelist_item_value " +
            "FROM urban_transport_ride_records INNER JOIN pricelist ON pricelist.pricelist_item_name = urban_transport_ride_records.ticket_type";

    static final String INSERT_RIDE_RECORD = "INSERT INTO urban_transport_ride_records (date, ticket_type, user_id) VALUES (?, ?, ?)";
    private final JdbcOperations jdbcOperations;

    public DefaultUrbanTransportRideRecordDao(JdbcOperations jdbcOperations) {
        this.jdbcOperations = Objects.requireNonNull(jdbcOperations);
    }

    @Override
    public Collection<UrbanTransportRideRecord> findAll() {
        return jdbcOperations.query(SELECT_ALL_RIDE_RECORDS, (rs, rowNum) -> {
            Timestamp timestamp = rs.getTimestamp("date");
            LocalDateTime rideDate = timestamp.toLocalDateTime();
            TicketType ticketType = TicketType.valueOf(rs.getString("ticket_type"));
            BigDecimal price = rs.getBigDecimal("pricelist_item_value");
            return new UrbanTransportRideRecord(rs.getLong("id"),
                    rideDate, price, ticketType, rs.getLong("user_id"));
        });
    }

    @Override
    public Collection<UrbanTransportRideRecord> findAll(TicketType ticketType) {
        return null;
    }

    @Override
    public Collection<UrbanTransportRideRecord> findAll(LocalDateTime localDateTime) {
        return null;
    }

    @Override
    public void insert(UrbanTransportRideRecord rideRecord) {
        jdbcOperations.update(INSERT_RIDE_RECORD,
                Timestamp.valueOf(rideRecord.getRideDateTime()), rideRecord.getTicketType().name(), rideRecord.getUserId());
    }
}
