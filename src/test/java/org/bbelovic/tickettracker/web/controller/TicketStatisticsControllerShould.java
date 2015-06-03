package org.bbelovic.tickettracker.web.controller;

import org.bbelovic.tickettracker.domain.TicketStatistics;
import org.bbelovic.tickettracker.domain.TicketStatisticsItem;
import org.bbelovic.tickettracker.domain.TicketType;
import org.bbelovic.tickettracker.service.RideComputationService;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ZERO;
import static java.time.Month.MAY;
import static java.util.Arrays.asList;
import static org.bbelovic.tickettracker.domain.TicketType.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TicketStatisticsControllerShould {
    @Test
    public void
    prepare_ticket_statistics_data_for_view() {
        RideComputationService rideComputationService = mock(RideComputationService.class);

        Set<TicketStatisticsItem> statisticsItems = prepareTicketStatisticsItems();
        TicketStatistics ticketStatistics = getTicketStatistics(statisticsItems);

        when(rideComputationService.computeTicketStatistics()).thenReturn(ticketStatistics);

        Map<String, Object> modelMap = new HashMap<>();
        TicketStatisticsController controller = new TicketStatisticsController(rideComputationService);
        String actualViewName = controller.prepareStatisticsData(modelMap);
        Map<String, Object> expectedModelMap = new HashMap<>();

        Map<YearMonth, EnumMap<TicketType, List<TicketStatisticsItem>>> ticketStats = new HashMap<>();
        ticketStatistics.getTicketStatistics().forEach((key, value)-> ticketStats.put(key, toEnumMap(value)));

        expectedModelMap.put("ticketStatistics", ticketStats);

        expectedModelMap.put("ticketTypes", EnumSet.complementOf(EnumSet.of(UNIVERSAL)));

        expectedModelMap.put("jspToInclude", "ticketStatistics");

        assertEquals("main", actualViewName);
        assertEquals(expectedModelMap, modelMap);

    }

    private Set<TicketStatisticsItem> prepareTicketStatisticsItems() {
        TicketStatisticsItem single15 = new TicketStatisticsItem(SINGLE_15, 0L, BigDecimal.valueOf(20L));
        TicketStatisticsItem single60 = new TicketStatisticsItem(SINGLE_60, 0L, BigDecimal.valueOf(25L));
        TicketStatisticsItem sms20 = new TicketStatisticsItem(SMS_20, 0L, BigDecimal.valueOf(20L));
        TicketStatisticsItem sms75 = new TicketStatisticsItem(SMS_75, 1L, BigDecimal.valueOf(29L));
        TicketStatisticsItem withoutTicket = new TicketStatisticsItem(WITHOUT_TICKET, 0L, ZERO);
        Set<TicketStatisticsItem> statisticsItems = new TreeSet<>(Comparator.comparing(TicketStatisticsItem::getTicketType));
        statisticsItems.addAll(asList(single15, single60, sms20, sms75, withoutTicket));
        return statisticsItems;
    }

    private EnumMap<TicketType, List<TicketStatisticsItem>> toEnumMap(Set<TicketStatisticsItem> statisticsItems) {
        EnumMap<TicketType, List<TicketStatisticsItem>> enumMap = new EnumMap<>(TicketType.class);
        Map<TicketType, List<TicketStatisticsItem>> collect = statisticsItems.stream()
                .collect(Collectors.groupingBy(TicketStatisticsItem::getTicketType));
        enumMap.putAll(collect);
        return enumMap;
    }

    private TicketStatistics getTicketStatistics(Set<TicketStatisticsItem> ticketStatisticsItems) {
        Map<YearMonth, Set<TicketStatisticsItem>> statisticsItemMap = new HashMap<>();
        statisticsItemMap.put(YearMonth.of(2015, MAY), ticketStatisticsItems);
        return new TicketStatistics(statisticsItemMap);
    }

}
