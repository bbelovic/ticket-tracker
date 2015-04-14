package org.bbelovic.tickettracker.domain;

import java.math.BigDecimal;
import java.util.Objects;

import static java.lang.String.format;

public class TicketStatisticsItem {
    private final TicketType ticketType;
    private final long ticketCount;
    private final BigDecimal ticketPrice;

    public TicketStatisticsItem(TicketType ticketType, long ticketCount, BigDecimal ticketPrice) {
        this.ticketType = ticketType;
        this.ticketCount = ticketCount;
        this.ticketPrice = ticketPrice;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public long getTicketCount() {
        return ticketCount;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketStatisticsItem that = (TicketStatisticsItem) o;
        return Objects.equals(ticketCount, that.ticketCount) &&
                Objects.equals(ticketType, that.ticketType) &&
                Objects.equals(ticketPrice, that.ticketPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketType, ticketCount, ticketPrice);
    }

    @Override
    public String toString() {
        return format("TicketStatisticsItem[ticketType=%s, ticketCount=%d, ticketPrice=%s]",
                ticketType, ticketCount, ticketPrice);
    }
}
