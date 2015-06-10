package org.bbelovic.tickettracker.domain;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Objects;

import static java.lang.String.format;

public class TicketStatisticsItem {

    private final YearMonth period;
    private final long single15;
    private final long single60;
    private final long sms20;
    private final long sms75;
    private final long withoutTicket;
    private final BigDecimal totalPeriodSum;


    public TicketStatisticsItem(YearMonth period, long single15, long single60, long sms20, long sms75,
                                long withoutTicket, BigDecimal totalPeriodSum) {
        this.period = period;
        this.single15 = single15;
        this.single60 = single60;
        this.sms20 = sms20;
        this.sms75 = sms75;
        this.withoutTicket = withoutTicket;
        this.totalPeriodSum = totalPeriodSum;
    }


    public YearMonth getPeriod() {
        return period;
    }

    public long getSingle15() {
        return single15;
    }

    public long getSingle60() {
        return single60;
    }

    public long getSms20() {
        return sms20;
    }

    public long getSms75() {
        return sms75;
    }

    public long getWithoutTicket() {
        return withoutTicket;
    }

    public BigDecimal getTotalPeriodSum() {
        return totalPeriodSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketStatisticsItem that = (TicketStatisticsItem) o;
        return Objects.equals(single15, that.single15) &&
                Objects.equals(single60, that.single60) &&
                Objects.equals(sms20, that.sms20) &&
                Objects.equals(sms75, that.sms75) &&
                Objects.equals(withoutTicket, that.withoutTicket) &&
                Objects.equals(period, that.period) &&
                Objects.equals(totalPeriodSum, that.totalPeriodSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(period, single15, single60, sms20, sms75, withoutTicket, totalPeriodSum);
    }

    @Override
    public String toString() {
        return format("DummyTicketStatisticsItem[period=%s, single15=%d, single60=%d, sms20=%d, sms75=%d, " +
                        "withoutTicket=%d, totalPeriodSum=%s]", period, single15, single60, sms20, sms75,
                withoutTicket, totalPeriodSum);
    }
}
