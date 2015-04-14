package org.bbelovic.tickettracker.domain;

import java.time.YearMonth;
import java.util.Map;
import java.util.Set;

public class TicketStatistics {
    private Map<YearMonth, Set<TicketStatisticsItem>> map;

    public TicketStatistics(Map<YearMonth, Set<TicketStatisticsItem>> map) {
        this.map = map;
    }

    public Map<YearMonth, Set<TicketStatisticsItem>> getMap() {
        return map;
    }

    public Map<YearMonth, Set<TicketStatisticsItem>> getTicketStatistics() {
        return map;
    }


}
