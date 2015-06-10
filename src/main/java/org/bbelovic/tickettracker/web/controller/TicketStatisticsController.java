package org.bbelovic.tickettracker.web.controller;

import org.bbelovic.tickettracker.domain.TicketStatistics;
import org.bbelovic.tickettracker.service.RideComputationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.EnumSet;
import java.util.Map;

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
        modelMap.put("ticketStatistics", ticketStatistics);
        modelMap.put("jspToInclude", "ticketStatistics");
        return "main";
    }

}
