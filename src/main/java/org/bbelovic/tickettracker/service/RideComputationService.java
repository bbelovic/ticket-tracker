package org.bbelovic.tickettracker.service;

import org.bbelovic.tickettracker.domain.TicketStatistics;

public interface RideComputationService {
    long computeTotalTicketPrice();
    TicketStatistics computeTicketStatistics();
}
