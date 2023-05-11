package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.*;
import com.example.hotelbooking.service.BookingService;
import com.example.hotelbooking.service.RoomService;
import com.example.hotelbooking.service.RoomStateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomStateService roomStateService;

    @GetMapping("/room/{id}")
    public String chooseRoom(@PathVariable Long id,
                             HttpSession session,
                             Model model) {
        Room room = roomService.findById(id);

        Booking booking = (Booking) session.getAttribute("booking"); // lấy booking từ session
        long numberOfNights = ChronoUnit.DAYS.between(booking.getCheckin(), booking.getCheckout());
        BigDecimal totalPrice = room.getCostPerNight().multiply(BigDecimal.valueOf(numberOfNights));
        booking.setRoom(room);
        booking.setTotalPrice(totalPrice);

        session.setAttribute("booking", booking);
        model.addAttribute("room",room);
        return "rooms-single";
    }

    @GetMapping("/payment")
    public String showPayment(HttpSession session, Model model){
        Booking booking = (Booking) session.getAttribute("booking");
        model.addAttribute("booking", booking);
        return "payment";
    }

    @PostMapping("/payment")
    public String confirm(Model model,
                          @RequestParam("note") String note,
                          HttpSession session){
        Booking booking = (Booking) session.getAttribute("booking");
        booking.setNote(note);
        booking.setStatus(Status.PENDING_CONFIRMATION);
        bookingService.createBooking(booking);

        roomStateService.setAvailableRoom(booking.getCheckin(),booking.getCheckout(),booking.getRoom());

        session.removeAttribute("booking");
        return "redirect:/account/history";
    }

    @GetMapping("/account/history")
    public String showHistory(Model model,
                              Authentication authentication){
        User user = (User) authentication.getPrincipal();
        List<Booking> bookings = bookingService.findByUserId(user.getId());
        model.addAttribute("bookings", bookings);
        return "history";
    }

    @GetMapping("/admin/statistic")
    public String showStatistic(Model model){
        List<Booking> bookings = bookingService.findAll();
        model.addAttribute("bookings", bookings);
        return "admin/booking";
    }

    @PostMapping("/admin/statistic")
    public String processStatistic(@RequestParam("month") String month,
                                   Model model){
        Statistic statistic = bookingService.statisticByMonth(month);
        model.addAttribute("statistic", statistic);
        return "admin/booking";
    }
    @PostMapping("/admin/booking/confirm/{id}")
    public String processConfirm(@PathVariable Long id){
        Booking booking = bookingService.findById(id);
        booking.setStatus(Status.CONFIRMED);
        bookingService.updateBooking(booking);
        return "redirect:/admin/statistic";
    }
    @PostMapping("/admin/booking/cancel/{id}")
    public String processCancel(@PathVariable Long id){
        Booking booking = bookingService.findById(id);
        booking.setStatus(Status.CANCELLED);
        bookingService.updateBooking(booking);
        roomStateService.removeAvailableRoom(booking.getCheckin(),booking.getCheckout(),booking.getRoom());
        return "redirect:/admin/statistic";
    }


}
