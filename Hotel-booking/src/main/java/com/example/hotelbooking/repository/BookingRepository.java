package com.example.hotelbooking.repository;

import com.example.hotelbooking.model.Booking;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByOrderByIdDesc();
    List<Booking> findAllByUserId(Long userId);
    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM booking b WHERE YEAR(b.checkin) = :year AND MONTH(b.checkin) = :month AND b.status = 1")
    BigDecimal getTotalPriceByMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT COUNT(DISTINCT b.user) FROM booking b WHERE YEAR(b.checkin) = :year AND MONTH(b.checkin) = :month AND b.status = 1")
    int countUsersByMonth(@Param("year") Integer year, @Param("month") Integer month);

    @Query("SELECT COUNT(b) FROM booking b WHERE YEAR(b.checkin) = :year AND MONTH(b.checkout) = :month  AND b.status = 1")
    int countBookingsByMonth(@Param("year") int year, @Param("month") int month);
    @Query("SELECT b FROM booking b WHERE YEAR(b.checkin) = :year AND MONTH(b.checkin) = :month ORDER BY b.id DESC")
    List<Booking> findByMonth(@Param("year") int year, @Param("month") int month);

}
