package com.example.hotelbooking.service;

import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.model.Statistic;
import org.hibernate.stat.Statistics;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {
    List<Booking> findAll();
    Booking findById(Long id);
    void createBooking(Booking booking);
    void updateBooking(Booking booking);
    void deleteBooking(Booking booking);
    List<Booking> findByUserId(Long userId);
    Statistic statisticByMonth(String month);

}
