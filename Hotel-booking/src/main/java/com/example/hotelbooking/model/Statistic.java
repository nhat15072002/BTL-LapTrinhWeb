package com.example.hotelbooking.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
@Builder
public class Statistic {
    private int numberOfBookings;
    private int numberOfUserBookings;
    private BigDecimal totalRevenue;
    private BigDecimal ADR;
    private List<Booking> bookings;
}
