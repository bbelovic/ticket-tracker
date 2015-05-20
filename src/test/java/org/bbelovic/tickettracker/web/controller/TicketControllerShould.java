package org.bbelovic.tickettracker.web.controller;

import org.bbelovic.tickettracker.dao.UrbanTransportRideRecordDao;
import org.bbelovic.tickettracker.domain.TicketType;
import org.bbelovic.tickettracker.domain.UrbanTransportRideRecord;
import org.bbelovic.tickettracker.service.Pricelist;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.bbelovic.tickettracker.domain.TicketType.SMS_75;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TicketControllerShould {

    private static final TicketType TICKET_TYPE_VALUE = SMS_75;
    private static final String RIDE_DATE_VALUE = "2015-04-23T11:10:20";
    private static final String USER_ID_VALUE = "1";
    private static final String TICKET_TYPE_KEY = "ticketType";
    private static final String RIDE_DATE_KEY = "rideDate";
    private static final String USER_ID_KEY = "userId";
    private static final long USER_ID = 1L;
    private static final String EXPECTED_VIEW_NAME = "main";
    private static final BigDecimal TICKET_PRICE = BigDecimal.valueOf(29L);

    @Test
    public void
    process_ticket_data_and_return_correct_view_name() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        UrbanTransportRideRecordDao urbanTransportRideRecordDao = mock(UrbanTransportRideRecordDao.class);
        Pricelist pricelist = mock(Pricelist.class);

        when(request.getParameter(TICKET_TYPE_KEY)).thenReturn(TICKET_TYPE_VALUE.name());
        when(request.getParameter(RIDE_DATE_KEY)).thenReturn(RIDE_DATE_VALUE);
        when(request.getParameter(USER_ID_KEY)).thenReturn(USER_ID_VALUE);

        when(pricelist.getPrice(TICKET_TYPE_VALUE)).thenReturn(TICKET_PRICE);

        LocalDateTime rideDate = LocalDateTime.parse(RIDE_DATE_VALUE);

        UrbanTransportRideRecord rideRecord =
                new UrbanTransportRideRecord(0L, rideDate, TICKET_PRICE, TICKET_TYPE_VALUE, USER_ID);

        Map<String, Object> modelMap = new HashMap<>();
        TicketController ticketController = new TicketController(urbanTransportRideRecordDao, pricelist);
        String actualViewName = ticketController.processTicket(request, modelMap);
        Map<String, Object> expectedModelMap = new HashMap<>();
        expectedModelMap.put("jspToInclude", "ticketResult");

        assertEquals(EXPECTED_VIEW_NAME, actualViewName);
        assertEquals(expectedModelMap, modelMap);

        verify(request).getParameter(TICKET_TYPE_KEY);
        verify(request).getParameter(RIDE_DATE_KEY);
        verify(request).getParameter(USER_ID_KEY);
        verify(urbanTransportRideRecordDao).insert(rideRecord);
        verify(pricelist).getPrice(TICKET_TYPE_VALUE);
    }

    @Test
    public void
    handle_request_for_new_ride_record_form() {
        UrbanTransportRideRecordDao urbanTransportRideRecordDao = mock(UrbanTransportRideRecordDao.class);
        Pricelist pricelist = mock(Pricelist.class);
        TicketController ticketController = new TicketController(urbanTransportRideRecordDao, pricelist);
        Map<String, Object> modelMap = new HashMap<>();
        String viewName = ticketController.newRideRecordForm(modelMap);
        assertEquals("main", viewName);
        Map<String, Object> expectedModelMap = new HashMap<>();
        expectedModelMap.put("jspToInclude", "ticketForm");
        assertEquals(expectedModelMap, modelMap);
        verifyZeroInteractions(urbanTransportRideRecordDao, pricelist);
    }
}
