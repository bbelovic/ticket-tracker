package org.bbelovic.tickettracker.service;

import java.math.BigDecimal;

public interface Pricelist {
    BigDecimal getPrice(String priceKey);
}
