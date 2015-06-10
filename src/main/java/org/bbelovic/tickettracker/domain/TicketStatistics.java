package org.bbelovic.tickettracker.domain;

import java.util.List;
import java.util.Objects;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class TicketStatistics {
    private final List<TicketStatisticsItem> items;
    private final long totalSum;


    public TicketStatistics(List<TicketStatisticsItem> items) {
        this.items = requireNonNull(items, "'items' can't be null");
        this.totalSum = items.stream().mapToLong(item->item.getTotalPeriodSum().longValue()).sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketStatistics that = (TicketStatistics) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    @Override
    public String toString() {
        return format("TicketStatistics[items=%s]", items);
    }

    public List<TicketStatisticsItem> getItems() {
        return items;
    }

    public long getTotalSum() {
        return totalSum;
    }
}
