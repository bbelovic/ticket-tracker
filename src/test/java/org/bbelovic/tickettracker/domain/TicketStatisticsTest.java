package org.bbelovic.tickettracker.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;

import static java.math.BigDecimal.ZERO;
import static org.junit.Assert.assertTrue;

public class TicketStatisticsTest {

    @Test
    public void
    equals_implementation_is_reflexive() {
        TicketStatistics ticketStatistics = createNewTicketStatistics();
        assertTrue(ticketStatistics.equals(ticketStatistics));
    }
    @Test
    public void
    equals_implementation_is_symmetric() {
        TicketStatistics first = createNewTicketStatistics();
        TicketStatistics second = createNewTicketStatistics();
        assertTrue(first.equals(second));
        assertTrue(second.equals(first));
    }
    @Test
    public void
    equals_implementation_is_transitive() {
        TicketStatistics first = createNewTicketStatistics();
        TicketStatistics second = createNewTicketStatistics();
        TicketStatistics third = createNewTicketStatistics();
        assertTrue(first.equals(second));
        assertTrue(second.equals(third));
        assertTrue(first.equals(third));
    }

    @Test(expected = NullPointerException.class)
    public void
    throw_NPE_when_provided_list_statistics_items_is_null() {
        new TicketStatistics(null);
    }

    private TicketStatistics createNewTicketStatistics() {
        DummyTicketStatisticsItem item1 = new DummyTicketStatisticsItem(YearMonth.of(2015, 6), 0, 0, 0, 0, 0, ZERO);
        DummyTicketStatisticsItem item2 = new DummyTicketStatisticsItem(YearMonth.of(2015, 5), 1, 1, 1, 1, 1, BigDecimal.valueOf(94L));
        return new TicketStatistics(Arrays.asList(item1, item2));
    }
}
