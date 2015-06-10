package org.bbelovic.tickettracker.service.impl;

import org.bbelovic.tickettracker.dao.UrbanTransportRideRecordDao;
import org.bbelovic.tickettracker.domain.TicketStatistics;
import org.bbelovic.tickettracker.domain.TicketStatisticsItem;
import org.bbelovic.tickettracker.domain.UrbanTransportRideRecord;
import org.bbelovic.tickettracker.service.RideComputationService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.*;
import java.util.Arrays;
import java.util.Collection;

import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static org.bbelovic.tickettracker.domain.TicketType.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RideComputationServiceShould {

    private RideComputationService rideComputationService;
    private UrbanTransportRideRecordDao rideRecordDao;

    @Before
    public void setUp() {
        rideRecordDao = mock(UrbanTransportRideRecordDao.class);
        when(rideRecordDao.findAll()).thenReturn(testData());
        rideComputationService = new DefaultRideComputationService(rideRecordDao, new TestPricelist());
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
        TicketStatistics expectedTicketStatistics = expectedTicketStatistics();
        TicketStatistics actualStatistics = rideComputationService.computeTicketStatistics();
        assertEquals(expectedTicketStatistics, actualStatistics);
    }

    private TicketStatistics expectedTicketStatistics() {
        TicketStatisticsItem march2015 =
                new TicketStatisticsItem(YearMonth.of(2015, Month.MARCH), 0L, 0L, 2L, 0L, 1L, BigDecimal.valueOf(40L));

        TicketStatisticsItem april2015 =
                new TicketStatisticsItem(YearMonth.of(2015, 4), 0, 0, 0, 1, 0, BigDecimal.valueOf(29L));

        TicketStatisticsItem may2015 =
                new TicketStatisticsItem(YearMonth.of(2015, 5), 0, 1, 0, 0, 1, BigDecimal.valueOf(25));

        TicketStatisticsItem february2015 =
                new TicketStatisticsItem(YearMonth.of(2015, 2), 0, 0, 1, 0, 0, BigDecimal.valueOf(20L));

        TicketStatisticsItem january2015 =
                new TicketStatisticsItem(YearMonth.of(2015, 1), 0, 0, 1, 0, 0, BigDecimal.valueOf(20L));

        return new TicketStatistics(Arrays.asList(january2015, february2015,
                march2015, april2015, may2015));
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
