package com.example.hotelbooking.service;

import com.example.hotelbooking.model.Room;
import com.example.hotelbooking.model.TypeOfRoom;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public interface RoomStateService {
    List<Room> findAvailableRooms(LocalDate start, LocalDate end, TypeOfRoom type);
    void setAvailableRoom(LocalDate checkin, LocalDate checkout, Room room);
    void removeAvailableRoom(LocalDate checkin, LocalDate checkout, Room room);
}
