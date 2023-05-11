package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.*;
import com.example.hotelbooking.service.RoomStateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class RoomStateController {
    @Autowired
    private RoomStateService roomStateService;

    @PostMapping("/search")
    public String process(@RequestParam("startDate") String startDate,
                          @RequestParam("endDate") String endDate,
                          @RequestParam("type") String type,
                          RedirectAttributes redirectAttributes,
                          HttpSession session,
                          Authentication authentication) {
        // chuyển ngày về định dạng của LocalDate
        String[] parts = startDate.split("/");
        startDate = String.format("%02d/%02d/%d", Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        parts = endDate.split("/");
        endDate = String.format("%02d/%02d/%d", Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate checkin = LocalDate.parse(startDate, formatter);
        LocalDate checkout = LocalDate.parse(endDate, formatter);

        // validate
        if (checkin.isBefore(LocalDate.now())) {
            redirectAttributes.addFlashAttribute("error", "Checkin date is in the past");
            return "redirect:/rooms";
        }
        if (checkout.isBefore(checkin)) {
            redirectAttributes.addFlashAttribute("error", "Checkin date must be before checkout date");
            return "redirect:/rooms";
        }

        // lưu thông tin booking vào session
        User user = (User) authentication.getPrincipal();
        Booking booking = Booking.builder()
                .checkin(checkin)
                .checkout(checkout)
                .user(user).build();
        session.setAttribute("booking", booking);

        // lấy ra danh sách phòng trống trả về view
        TypeOfRoom typeOfRoom = TypeOfRoom.valueOf(type);
        List<Room> rooms = roomStateService.findAvailableRooms(checkin, checkout, typeOfRoom);
        redirectAttributes.addFlashAttribute("roomsSearch", rooms);

        return "redirect:/rooms";
    }

}
