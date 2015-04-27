package org.bbelovic.tickettracker.web.controller;

import org.bbelovic.tickettracker.dao.UrbanTransportRideRecordDao;
import org.bbelovic.tickettracker.domain.TicketType;
import org.bbelovic.tickettracker.domain.UrbanTransportRideRecord;
import org.bbelovic.tickettracker.service.Pricelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class TicketController {

    private final UrbanTransportRideRecordDao urbanTransportRideRecordDao;
    private final Pricelist pricelist;

    @Autowired
    public TicketController(UrbanTransportRideRecordDao urbanTransportRideRecordDao, Pricelist pricelist) {
        this.urbanTransportRideRecordDao = urbanTransportRideRecordDao;
        this.pricelist = pricelist;
    }

    @RequestMapping(value = "ticketController", method = POST)
    public String processTicket(HttpServletRequest request) {
        long userId = Long.parseLong(request.getParameter("userId"));
        TicketType ticketType = TicketType.valueOf(request.getParameter("ticketType"));
        LocalDateTime rideDate = LocalDateTime.parse(request.getParameter("rideDate"));
        BigDecimal ticketPrice = pricelist.getPrice(ticketType.name());
        UrbanTransportRideRecord rideRecord = new UrbanTransportRideRecord(0L, rideDate, ticketPrice, ticketType, userId);
        System.out.println("Adding new rideRecord="+rideRecord);
        urbanTransportRideRecordDao.insert(rideRecord);
        return "ticketResult";

    }
}
