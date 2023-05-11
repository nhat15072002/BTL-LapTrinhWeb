package com.example.hotelbooking.service;

import com.example.hotelbooking.model.Room;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    List<Room> getAllRoom();
    void add(Room room);
    void update(Room room);
    void delete(Long id);

    Room findById(Long id);

    Room findByName(String name);

    List<Room> findAllActiveRooms();
}
