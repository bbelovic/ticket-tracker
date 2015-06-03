package org.bbelovic.tickettracker.domain;

import org.junit.Test;

import java.time.Month;
import java.time.YearMonth;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.math.BigDecimal.ZERO;
import static org.bbelovic.tickettracker.domain.TicketType.WITHOUT_TICKET;
import static org.junit.Assert.assertTrue;

public class TicketStatisticsShould {

    @Test
    public void
    be_reflexive() {
        TicketStatistics ticketStatistics = createNewTicketStatistics();
        assertTrue(ticketStatistics.equals(ticketStatistics));
    }
    @Test
    public void
    be_symmetric() {
        TicketStatistics first = createNewTicketStatistics();
        TicketStatistics second = createNewTicketStatistics();
        assertTrue(first.equals(second));
        assertTrue(second.equals(first));
    }
    @Test
    public void
    be_transitive() {
        TicketStatistics first = createNewTicketStatistics();
        TicketStatistics second = createNewTicketStatistics();
        TicketStatistics third = createNewTicketStatistics();
        assertTrue(first.equals(second));
        assertTrue(second.equals(third));
        assertTrue(first.equals(third));
    }

    private TicketStatistics createNewTicketStatistics() {
        TicketStatisticsItem item = new TicketStatisticsItem(WITHOUT_TICKET, 0L, ZERO);
        Map<YearMonth, Set<TicketStatisticsItem>> data = new HashMap<>();
        data.put(YearMonth.of(2015, Month.APRIL), Collections.singleton(item));
        return new TicketStatistics(data);
    }
}
