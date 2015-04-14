package org.bbelovic.tickettracker.service.impl;

import org.bbelovic.tickettracker.dao.UrbanTransportRideRecordDao;
import org.bbelovic.tickettracker.domain.TicketStatistics;
import org.bbelovic.tickettracker.domain.TicketStatisticsItem;
import org.bbelovic.tickettracker.domain.UrbanTransportRideRecord;
import org.bbelovic.tickettracker.service.Pricelist;
import org.bbelovic.tickettracker.service.RideComputationService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.*;

import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.bbelovic.tickettracker.domain.TicketType.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RideComputationServiceShould {

    private RideComputationService rideComputationService;
    private UrbanTransportRideRecordDao rideRecordDao;
    private Pricelist pricelist;

    @Before
    public void setUp() {
        rideRecordDao = mock(UrbanTransportRideRecordDao.class);
        when(rideRecordDao.findAll()).thenReturn(testData());
        pricelist = new TestPricelist();
        rideComputationService = new DefaultRideComputationService(rideRecordDao, pricelist);
    }


    @Test
    public void
    compute_total_price_for_all_ride_records_in_the_system() {
        long actual = rideComputationService.computeTotalTicketPrice();
        assertEquals(147, actual);
        verify(rideRecordDao).findAll();
    }

    @Test
    public void
    compute_ticket_statistics_for_each_month() {
        Map<YearMonth, Set<TicketStatisticsItem>> expectedItems = expectedStatisticsItems();

        TicketStatistics actualStatistics = rideComputationService.computeTicketStatistics();
        Map<YearMonth, Set<TicketStatisticsItem>> actualItems =
                actualStatistics.getTicketStatistics();

        assertEquals(expectedItems, actualItems);
    }

    private Map<YearMonth, Set<TicketStatisticsItem>> expectedStatisticsItems() {
        Map<YearMonth, Set<TicketStatisticsItem>> expectedItems = new TreeMap<>(YearMonth::compareTo);

        TicketStatisticsItem item1 = new TicketStatisticsItem(SMS_20, 2, BigDecimal.valueOf(40L));
        TicketStatisticsItem item2 = new TicketStatisticsItem(WITHOUT_TICKET, 1, BigDecimal.ZERO);
        expectedItems.put(YearMonth.of(2015, 3), new HashSet<>(asList(item1, item2)));

        TicketStatisticsItem item3 = new TicketStatisticsItem(SMS_75, 1, BigDecimal.valueOf(29L));
        expectedItems.put(YearMonth.of(2015, 4), new HashSet<>(singletonList(item3)));

        TicketStatisticsItem item4 = new TicketStatisticsItem(SINGLE_60, 1, BigDecimal.valueOf(20L));
        TicketStatisticsItem item5 = new TicketStatisticsItem(WITHOUT_TICKET, 1, BigDecimal.ZERO);
        expectedItems.put(YearMonth.of(2015, 5), new HashSet<>(asList(item4, item5)));

        TicketStatisticsItem item6 = new TicketStatisticsItem(SMS_20, 1, BigDecimal.valueOf(20L));
        expectedItems.put(YearMonth.of(2015, 2), new HashSet<>(singletonList(item6)));

        TicketStatisticsItem item7 = new TicketStatisticsItem(SMS_20, 1, BigDecimal.valueOf(20L));
        expectedItems.put(YearMonth.of(2015, 1), new HashSet<>(singletonList(item7)));
        return expectedItems;
    }

    static Collection<UrbanTransportRideRecord> testData() {
        UrbanTransportRideRecord record1 =
                new UrbanTransportRideRecord(1L,
                        LocalDateTime.of(LocalDate.of(2015, 1, 1), LocalTime.of(2, 20, 0, 0)),
                        new BigDecimal("29"), SMS_20, 10L);
        UrbanTransportRideRecord record2 =
                new UrbanTransportRideRecord(2L,
                        LocalDateTime.of(LocalDate.of(2015, 2, 2), LocalTime.of(12, 0, 0, 0)),
                        new BigDecimal("29"), SMS_20, 10L);
        UrbanTransportRideRecord record3 =
                new UrbanTransportRideRecord(3L,
                        LocalDateTime.of(LocalDate.of(2015, 3, 1), LocalTime.of(13, 0, 0, 0)),
                        new BigDecimal("20"), SMS_20, 10L);
        UrbanTransportRideRecord record4 =
                new UrbanTransportRideRecord(4L,
                        LocalDateTime.of(LocalDate.of(2015, 4, 1), LocalTime.of(13, 0, 0, 0)),
                        new BigDecimal("29"), SMS_75, 10L);
        UrbanTransportRideRecord record5 =
                new UrbanTransportRideRecord(5L,
                        LocalDateTime.of(LocalDate.of(2015, 5, 10), LocalTime.of(13, 0, 0, 0)),
                        new BigDecimal("20"), SINGLE_60, 10L);
        UrbanTransportRideRecord record6 =
                new UrbanTransportRideRecord(6L,
                        LocalDateTime.of(LocalDate.of(2015, 5, 10), LocalTime.of(13, 0, 0, 0)),
                        ZERO, WITHOUT_TICKET, 10L);
        UrbanTransportRideRecord record7 =
                new UrbanTransportRideRecord(7L,
                        LocalDateTime.of(LocalDate.of(2015, 3, 10), LocalTime.of(13, 0, 0, 0)),
                        ZERO, WITHOUT_TICKET, 10L);
        UrbanTransportRideRecord record8 =
                new UrbanTransportRideRecord(8L,
                        LocalDateTime.of(LocalDate.of(2015, 3, 10), LocalTime.of(13, 0, 0, 0)),
                        new BigDecimal("20"), SMS_20, 10L);

        return asList(record1, record2, record3, record4, record5, record6, record7, record8);
    }
}
