package org.bbelovic.tickettracker.service.impl;

import org.bbelovic.tickettracker.dao.UrbanTransportRideRecordDao;
import org.bbelovic.tickettracker.domain.TicketStatistics;
import org.bbelovic.tickettracker.domain.TicketStatisticsItem;
import org.bbelovic.tickettracker.domain.TicketType;
import org.bbelovic.tickettracker.domain.UrbanTransportRideRecord;
import org.bbelovic.tickettracker.service.Pricelist;
import org.bbelovic.tickettracker.service.RideComputationService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;

import static java.util.stream.Collectors.*;
import static org.bbelovic.tickettracker.domain.TicketType.UNIVERSAL;

public class DefaultRideComputationService implements RideComputationService {
    private final UrbanTransportRideRecordDao urbanTransportRideRecordDao;
    private final Pricelist pricelist;

    private final Function<Map<TicketType, Long>, Set<TicketStatisticsItem>> toTicketStatistics = mapToTicketStatistics();

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

        Map<YearMonth, Map<TicketType, Long>> rawTicketStatsByMonth = rideRecords
                .stream()
                .collect(groupingBy(this::getYearMonthFromRideRecord, ticketCountByTicketType));

        Map<YearMonth, Set<TicketStatisticsItem>> result = new TreeMap<>(YearMonth::compareTo);
        rawTicketStatsByMonth.forEach((yearMonth, ticketCount) -> result.put(yearMonth, toTicketStatistics.apply(ticketCount)));
        BiConsumer<YearMonth, Set<TicketStatisticsItem>> biConsumer = (key, value) -> {
            EnumSet<TicketType> ticketTypes = EnumSet.complementOf(EnumSet.of(UNIVERSAL));
            ticketTypes.removeAll(value.stream().map(TicketStatisticsItem::getTicketType).collect(toList()));
            ticketTypes.forEach(ticketType -> {
                TicketStatisticsItem empty = new TicketStatisticsItem(ticketType, 0L, pricelist.getPrice(ticketType));
                value.add(empty);
            });
        };
        result.forEach(biConsumer);
        return new TicketStatistics(result);
    }

    private Function<Map<TicketType, Long>, Set<TicketStatisticsItem>> mapToTicketStatistics() {
        return m -> {
            Set<TicketStatisticsItem> items = new HashSet<>();
            m.forEach((type, count) -> items.add(createTicketStatisticsItem(type, count)));
            return items;
        };
    }

    private TicketStatisticsItem createTicketStatisticsItem(TicketType type, Long count) {
        BigDecimal priceValue = pricelist.getPrice(type);
        BigDecimal finalPrice = priceValue.multiply(BigDecimal.valueOf(count));
        return new TicketStatisticsItem(type, count, finalPrice);
    }

    private YearMonth getYearMonthFromRideRecord(UrbanTransportRideRecord rideRecord) {
        LocalDateTime dateTime = rideRecord.getRideDateTime();
        return YearMonth.of(dateTime.getYear(), dateTime.getMonthValue());
    }


}
