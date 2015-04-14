package org.bbelovic.tickettracker.service.impl;

import org.bbelovic.tickettracker.service.Pricelist;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

class TestPricelist implements Pricelist {
    @Override
    public BigDecimal getPrice(String priceKey) {
        Map<String, BigDecimal> pricelist = new HashMap<>();
        pricelist.put("SMS_20", BigDecimal.valueOf(20L));
        pricelist.put("SMS_75", BigDecimal.valueOf(29L));
        pricelist.put("SINGLE_60", BigDecimal.valueOf(20L));
        pricelist.put("WITHOUT_TICKET", BigDecimal.ZERO);
        return pricelist.get(priceKey);
    }
}
