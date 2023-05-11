package com.example.hotelbooking.service.impl;

import com.example.hotelbooking.model.Room;
import com.example.hotelbooking.model.RoomState;
import com.example.hotelbooking.model.TypeOfRoom;
import com.example.hotelbooking.repository.RoomStateRepository;
import com.example.hotelbooking.service.RoomStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class RoomStateServiceImpl implements RoomStateService {
    @Autowired
    private RoomStateRepository repository;

    @Override
    public List<Room> findAvailableRooms(LocalDate start, LocalDate end, TypeOfRoom type) {
        return repository.findAvailableRooms(start, end, type);
    }

    // lưu các ngày được đặt vào csdl
    @Override
    public void setAvailableRoom(LocalDate checkin, LocalDate checkout, Room room) {
        for (LocalDate date = checkin; date.isBefore(checkout.plusDays(1)); date = date.plusDays(1)) {
            RoomState roomState = RoomState.builder()
                    .room(room)
                    .date(date).build();
            repository.save(roomState);
        }
    }

    @Override
    public void removeAvailableRoom(LocalDate checkin, LocalDate checkout, Room room) {
        for (LocalDate date = checkin; date.isBefore(checkout.plusDays(1)); date = date.plusDays(1)) {
            RoomState roomState = repository.findByRoomAndDate(room, date);
            repository.delete(roomState);
        }
    }

}
