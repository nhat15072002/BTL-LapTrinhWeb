package com.example.hotelbooking.service.impl;

import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.model.Statistic;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository repository;

    @Override
    public List<Booking> findAll() {
        return repository.findAllByOrderByIdDesc();
    }

    @Override
    public Booking findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalStateException("Not found booking " + id));
    }

    @Override
    public void createBooking(Booking booking) {
        booking.setCreatedAt(LocalDate.now());
        repository.save(booking);
    }

    @Override
    public void updateBooking(Booking booking) {
        repository.save(booking);
    }

    @Override
    public void deleteBooking(Booking booking) {

    }

    @Override
    public List<Booking> findByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public Statistic statisticByMonth(String monthString) {
        YearMonth yearMonth = YearMonth.parse(monthString);
        int year = yearMonth.getYear();
        int month = yearMonth.getMonthValue();

        int numberOfBookings = repository.countBookingsByMonth(year,month);
        int numberOfUserBookings = repository.countUsersByMonth(year,month);
        BigDecimal totalRevenue = repository.getTotalPriceByMonth(year,month);
        BigDecimal ADR = totalRevenue.divide(BigDecimal.valueOf(yearMonth.lengthOfMonth()), RoundingMode.HALF_UP);
        List<Booking> bookings = repository.findByMonth(year,month);

        Statistic statistic = Statistic.builder()
                .numberOfBookings(numberOfBookings)
                .numberOfUserBookings(numberOfUserBookings)
                .totalRevenue(totalRevenue)
                .ADR(ADR)
                .bookings(bookings).build();
        return statistic;
    }
}
