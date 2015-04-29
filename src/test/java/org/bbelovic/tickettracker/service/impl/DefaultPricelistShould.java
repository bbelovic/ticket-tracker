package org.bbelovic.tickettracker.service.impl;

import org.bbelovic.tickettracker.domain.TicketType;
import org.bbelovic.tickettracker.service.Pricelist;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;

import static org.bbelovic.tickettracker.domain.TicketType.SMS_20;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class DefaultPricelistShould {

    private static final TicketType PRICE_KEY = SMS_20;
    private static final BigDecimal EXPECTED_TICKET_PRICE = BigDecimal.ONE;

    @Test
    public void
    return_price_value_for_given_key() throws Exception {
        JdbcOperations jdbcOperations = mock(JdbcOperations.class);
        String query = "SELECT pricelist_item_value FROM pricelist WHERE pricelist_item_name=?";

        when(jdbcOperations.queryForObject(eq(query), any(RowMapper.class), eq(PRICE_KEY.name())))
                .thenReturn(EXPECTED_TICKET_PRICE);

        Pricelist pricelist = new DefaultPricelist(jdbcOperations);
        BigDecimal actualTicketPrice = pricelist.getPrice(PRICE_KEY);
        assertEquals(EXPECTED_TICKET_PRICE, actualTicketPrice);

        verify(jdbcOperations).queryForObject(eq(query), any(RowMapper.class), eq(PRICE_KEY.name()));
    }

}
