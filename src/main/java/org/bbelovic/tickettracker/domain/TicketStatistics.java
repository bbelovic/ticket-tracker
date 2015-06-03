package org.bbelovic.tickettracker.domain;

import java.time.YearMonth;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.lang.String.format;

public class TicketStatistics {
    private Map<YearMonth, Set<TicketStatisticsItem>> map;

    public TicketStatistics(Map<YearMonth, Set<TicketStatisticsItem>> map) {
        this.map = map;
    }

    public Map<YearMonth, Set<TicketStatisticsItem>> getTicketStatistics() {
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketStatistics that = (TicketStatistics) o;
        return Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }

    @Override
    public String toString() {
        return format("TicketStatistics[data=%s]", map);
    }

}
