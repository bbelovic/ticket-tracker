package org.bbelovic.tickettracker.service;

import org.bbelovic.tickettracker.domain.TicketType;

import java.math.BigDecimal;

public interface Pricelist {
    BigDecimal getPrice(TicketType ticketType);
}
