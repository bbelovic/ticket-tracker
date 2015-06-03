package org.bbelovic.tickettracker.web.controller;

import org.bbelovic.tickettracker.domain.TicketStatistics;
import org.bbelovic.tickettracker.domain.TicketStatisticsItem;
import org.bbelovic.tickettracker.domain.TicketType;
import org.bbelovic.tickettracker.service.RideComputationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static org.bbelovic.tickettracker.domain.TicketType.UNIVERSAL;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class TicketStatisticsController {

    private final RideComputationService rideComputationService;

    @Autowired
    public TicketStatisticsController(RideComputationService rideComputationService) {
        this.rideComputationService = requireNonNull(rideComputationService, "rideComputationService can't be null");
    }

    @RequestMapping(value = "ticketStatistics", method = GET)
    public String prepareStatisticsData(Map<String, Object> modelMap) {
        TicketStatistics ticketStatistics = rideComputationService.computeTicketStatistics();
        modelMap.put("ticketTypes", EnumSet.complementOf(EnumSet.of(UNIVERSAL)));
        modelMap.put("ticketStatistics", processStatisticsItems(ticketStatistics));
        modelMap.put("jspToInclude", "ticketStatistics");
        return "main";
    }

    private Map<YearMonth, EnumMap<TicketType,List<TicketStatisticsItem>>> processStatisticsItems(TicketStatistics ticketStatistics) {
        Map<YearMonth, EnumMap<TicketType, List<TicketStatisticsItem>>> result = new HashMap<>();
        ticketStatistics.getTicketStatistics()
                .forEach((key, value) -> {
                    Map<TicketType, List<TicketStatisticsItem>> collected = value.stream()
                            .collect(Collectors.groupingBy(TicketStatisticsItem::getTicketType));
                    EnumMap<TicketType, List<TicketStatisticsItem>> enumMap = new EnumMap<>(TicketType.class);
                    enumMap.putAll(collected);
                    result.put(key, enumMap);
                });
        return result;
    }

}
