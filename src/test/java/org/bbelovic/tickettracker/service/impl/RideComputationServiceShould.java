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

        TicketStatisticsItem item1 = new TicketStatisticsItem(SMS_20, 2, BigDecimal.valueOf(20L));
        TicketStatisticsItem item1_sms75 = new TicketStatisticsItem(SMS_75, 0, BigDecimal.valueOf(29L));
        TicketStatisticsItem item1_single15 = new TicketStatisticsItem(SINGLE_15, 0, BigDecimal.valueOf(20L));
        TicketStatisticsItem item1_single60 = new TicketStatisticsItem(SINGLE_60, 0, BigDecimal.valueOf(25L));
        TicketStatisticsItem item1_withoutTicket = new TicketStatisticsItem(WITHOUT_TICKET, 1, ZERO);
        Comparator<TicketStatisticsItem> comparator = Comparator.comparing(TicketStatisticsItem::getTicketType);
        Set<TicketStatisticsItem> set1 = new TreeSet<>(comparator);
        set1.addAll(asList(item1, item1_sms75, item1_single15, item1_single60, item1_withoutTicket));
        expectedItems.put(YearMonth.of(2015, 3), set1);

        TicketStatisticsItem item2_sms75 = new TicketStatisticsItem(SMS_75, 1, BigDecimal.valueOf(29L));
        TicketStatisticsItem item2_sms20 = new TicketStatisticsItem(SMS_20, 0, BigDecimal.valueOf(20L));
        TicketStatisticsItem item2_single15 = new TicketStatisticsItem(SINGLE_15, 0, BigDecimal.valueOf(20L));
        TicketStatisticsItem item2_single60 = new TicketStatisticsItem(SINGLE_60, 0, BigDecimal.valueOf(25L));
        TicketStatisticsItem item2_withoutTicket = new TicketStatisticsItem(WITHOUT_TICKET, 0, ZERO);
        Set<TicketStatisticsItem> set2 = new TreeSet<>(comparator);
        set2.addAll(asList(item2_sms20, item2_sms75, item2_single15, item2_single60, item2_withoutTicket));
        expectedItems.put(YearMonth.of(2015, 4), set2);

        TicketStatisticsItem item3_single15 = new TicketStatisticsItem(SINGLE_15, 0, BigDecimal.valueOf(20L));
        TicketStatisticsItem item3_sms75 = new TicketStatisticsItem(SMS_75, 0, BigDecimal.valueOf(29L));
        TicketStatisticsItem item3_sms20 = new TicketStatisticsItem(SMS_20, 0, BigDecimal.valueOf(20L));
        TicketStatisticsItem item3_single60 = new TicketStatisticsItem(SINGLE_60, 1, BigDecimal.valueOf(25L));
        TicketStatisticsItem item3_withoutTicket = new TicketStatisticsItem(WITHOUT_TICKET, 1, ZERO);
        Set<TicketStatisticsItem> set3 = new TreeSet<>(comparator);
        set3.addAll(asList(item3_single15, item3_sms20, item3_single60, item3_sms75, item3_withoutTicket));
        expectedItems.put(YearMonth.of(2015, 5), set3);

        TicketStatisticsItem item4_sms20 = new TicketStatisticsItem(SMS_20, 1, BigDecimal.valueOf(20L));
        TicketStatisticsItem item4_sms75 = new TicketStatisticsItem(SMS_75, 0, BigDecimal.valueOf(29L));
        TicketStatisticsItem item4_single15 = new TicketStatisticsItem(SINGLE_15, 0, BigDecimal.valueOf(20L));
        TicketStatisticsItem item4_single60 = new TicketStatisticsItem(SINGLE_60, 0, BigDecimal.valueOf(25L));
        TicketStatisticsItem item4_withoutTicket = new TicketStatisticsItem(WITHOUT_TICKET, 0, ZERO);
        Set<TicketStatisticsItem> set4 = new TreeSet<>(comparator);
        set4.addAll(asList(item4_single15, item4_single60, item4_sms20, item4_sms75, item4_withoutTicket));
        expectedItems.put(YearMonth.of(2015, 2), set4);

        TicketStatisticsItem item5_sms20 = new TicketStatisticsItem(SMS_20, 1, BigDecimal.valueOf(20L));
        TicketStatisticsItem item5_sms75 = new TicketStatisticsItem(SMS_75, 0, BigDecimal.valueOf(29L));
        TicketStatisticsItem item5_single15 = new TicketStatisticsItem(SINGLE_15, 0, BigDecimal.valueOf(20L));
        TicketStatisticsItem item5_single60 = new TicketStatisticsItem(SINGLE_60, 0, BigDecimal.valueOf(25L));
        TicketStatisticsItem item5_withoutTicket = new TicketStatisticsItem(WITHOUT_TICKET, 0, ZERO);
        Set<TicketStatisticsItem> set5 = new TreeSet<>(comparator);
        set5.addAll(asList(item5_sms20, item5_single15, item5_single60, item5_sms75, item5_withoutTicket));

        expectedItems.put(YearMonth.of(2015, 1), set5);
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
