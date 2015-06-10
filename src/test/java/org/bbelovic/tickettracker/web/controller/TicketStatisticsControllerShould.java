package org.bbelovic.tickettracker.web.controller;

import org.bbelovic.tickettracker.domain.DummyTicketStatisticsItem;
import org.bbelovic.tickettracker.domain.TicketStatistics;
import org.bbelovic.tickettracker.service.RideComputationService;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.bbelovic.tickettracker.domain.TicketType.UNIVERSAL;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TicketStatisticsControllerShould {

    @Test
    public void
    prepare_ticket_statistics_data_and_view_information() {
        RideComputationService rideComputationService = mock(RideComputationService.class);

        TicketStatistics ticketStatistics = getTicketStatistics();

        when(rideComputationService.computeTicketStatistics()).thenReturn(ticketStatistics);

        Map<String, Object> modelMap = new HashMap<>();
        TicketStatisticsController controller = new TicketStatisticsController(rideComputationService);
        String actualViewName = controller.prepareStatisticsData(modelMap);
        Map<String, Object> expectedModelMap = new HashMap<>();


        expectedModelMap.put("ticketStatistics", ticketStatistics);
        expectedModelMap.put("ticketTypes", EnumSet.complementOf(EnumSet.of(UNIVERSAL)));
        expectedModelMap.put("jspToInclude", "ticketStatistics");

        assertEquals("main", actualViewName);
        assertEquals(expectedModelMap, modelMap);
        assertEquals(expectedModelMap.get("ticketStatistics"), modelMap.get("ticketStatistics"));

    }

    private TicketStatistics getTicketStatistics() {
        DummyTicketStatisticsItem item = new DummyTicketStatisticsItem(YearMonth.of(2015, 5), 1, 0, 0, 0, 0, BigDecimal.valueOf(20L));
        return new TicketStatistics(asList(item));
    }

}
