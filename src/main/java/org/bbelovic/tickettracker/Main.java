package org.bbelovic.tickettracker;

import org.bbelovic.tickettracker.config.AppConfiguration;
import org.bbelovic.tickettracker.dao.UrbanTransportRideRecordDao;
import org.bbelovic.tickettracker.dao.UserDao;
import org.bbelovic.tickettracker.domain.TicketType;
import org.bbelovic.tickettracker.domain.UrbanTransportRideRecord;
import org.bbelovic.tickettracker.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        UserDao userDao = applicationContext.getBean(UserDao.class);
        LocalDateTime createdAt = LocalDateTime.of(2015, 3, 24, 23, 36, 54);
        User user = new User(1L, "bbelovic", "aaaa", "bbelovic@gmail.com", createdAt, "Europe/Prague");
        userDao.update(user);


//        UrbanTransportRideRecordDao dao = applicationContext.getBean(UrbanTransportRideRecordDao.class);
//        LocalDateTime time = LocalDateTime.of(2015, Month.APRIL, 12, 19, 5);
//        dao.insert(new UrbanTransportRideRecord(1l, time, BigDecimal.ONE, TicketType.SINGLE_15, 1L));
    }
}
