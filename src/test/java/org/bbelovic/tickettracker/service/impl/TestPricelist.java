package org.bbelovic.tickettracker.service.impl;

import org.bbelovic.tickettracker.domain.TicketType;
import org.bbelovic.tickettracker.service.Pricelist;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

class TestPricelist implements Pricelist {
    @Override
    public BigDecimal getPrice(TicketType ticketType) {
        Map<TicketType, BigDecimal> pricelist = new HashMap<>();
        pricelist.put(TicketType.SMS_20, BigDecimal.valueOf(20L));
        pricelist.put(TicketType.SMS_75, BigDecimal.valueOf(29L));
        pricelist.put(TicketType.SINGLE_60, BigDecimal.valueOf(20L));
        pricelist.put(TicketType.WITHOUT_TICKET, BigDecimal.ZERO);
        return pricelist.get(ticketType);
    }
}
