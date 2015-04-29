package org.bbelovic.tickettracker.service.impl;

import org.bbelovic.tickettracker.domain.TicketType;
import org.bbelovic.tickettracker.service.Pricelist;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class DefaultPricelist implements Pricelist {
    private final JdbcOperations jdbcOperations;

    public DefaultPricelist(JdbcOperations jdbcOperations) {
        this.jdbcOperations = Objects.requireNonNull(jdbcOperations);
    }

    @Override
    public BigDecimal getPrice(TicketType ticketType) {
        return jdbcOperations.queryForObject("SELECT pricelist_item_value FROM pricelist WHERE pricelist_item_name=?",
                this::extractPriceFromResultSet, ticketType.name());
    }

    private BigDecimal extractPriceFromResultSet(ResultSet resultSet, int rowNum) {
        try {
            return resultSet.getBigDecimal("pricelist_item_value");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
