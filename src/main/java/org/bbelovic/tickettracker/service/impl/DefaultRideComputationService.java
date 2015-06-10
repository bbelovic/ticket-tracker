package org.bbelovic.tickettracker.service.impl;

import org.bbelovic.tickettracker.dao.UrbanTransportRideRecordDao;
import org.bbelovic.tickettracker.domain.TicketStatistics;
import org.bbelovic.tickettracker.domain.TicketType;
import org.bbelovic.tickettracker.domain.UrbanTransportRideRecord;
import org.bbelovic.tickettracker.service.Pricelist;
import org.bbelovic.tickettracker.service.RideComputationService;
import org.bbelovic.tickettracker.domain.TicketStatisticsItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collector;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.bbelovic.tickettracker.domain.TicketType.*;

public class DefaultRideComputationService implements RideComputationService {
    private final UrbanTransportRideRecordDao urbanTransportRideRecordDao;
    private final Pricelist pricelist;

    public DefaultRideComputationService(UrbanTransportRideRecordDao urbanTransportRideRecordDao, Pricelist pricelist) {
        this.urbanTransportRideRecordDao = urbanTransportRideRecordDao;
        this.pricelist = pricelist;
    }

    @Override
    public long computeTotalTicketPrice() {
        Collection<UrbanTransportRideRecord> rideRecords = urbanTransportRideRecordDao.findAll();
        return rideRecords.stream()
                .mapToLong(record -> record.getPrice().longValue())
                .sum();
    }

    @Override
    public TicketStatistics computeTicketStatistics() {
        Collection<UrbanTransportRideRecord> rideRecords = urbanTransportRideRecordDao.findAll();

        Collector<UrbanTransportRideRecord, ?, Map<TicketType, Long>> ticketCountByTicketType =
                groupingBy(UrbanTransportRideRecord::getTicketType, counting());

        List<TicketStatisticsItem> items = new ArrayList<>();
                rideRecords
                .stream()
                .collect(groupingBy(this::getYearMonthFromRideRecord, ticketCountByTicketType))
                .forEach((key, value) -> {
                    long single15 = value.getOrDefault(SINGLE_15, 0L);
                    long single60 = value.getOrDefault(SINGLE_60, 0L);
                    long sms20 = value.getOrDefault(SMS_20, 0L);
                    long sms75 = value.getOrDefault(SMS_75, 0L);
                    long withoutTicket = value.getOrDefault(WITHOUT_TICKET, 0L);
                    BigDecimal price15 = BigDecimal.valueOf(single15).multiply(pricelist.getPrice(SINGLE_15));
                    BigDecimal price60 = BigDecimal.valueOf(single60).multiply(pricelist.getPrice(SINGLE_60));
                    BigDecimal price20 = BigDecimal.valueOf(sms20).multiply(pricelist.getPrice(SMS_20));
                    BigDecimal price75 = BigDecimal.valueOf(sms75).multiply(pricelist.getPrice(SMS_75));
                    BigDecimal totalSum = price15.add(price60).add(price20).add(price75);
                    TicketStatisticsItem item = new TicketStatisticsItem(key, single15, single60,
                            sms20, sms75, withoutTicket, totalSum);
                    items.add(item);
                });
        items.sort(Comparator.comparing(TicketStatisticsItem::getPeriod));
        return new TicketStatistics(items);
    }

    private YearMonth getYearMonthFromRideRecord(UrbanTransportRideRecord rideRecord) {
        LocalDateTime dateTime = rideRecord.getRideDateTime();
        return YearMonth.of(dateTime.getYear(), dateTime.getMonthValue());
    }


}
