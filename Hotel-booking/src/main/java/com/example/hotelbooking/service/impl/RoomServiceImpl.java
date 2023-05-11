package com.example.hotelbooking.service.impl;

import com.example.hotelbooking.exception.RoomAlreadyExistsException;
import com.example.hotelbooking.exception.UserAlreadyExistsException;
import com.example.hotelbooking.model.Room;
import com.example.hotelbooking.model.User;
import com.example.hotelbooking.repository.RoomRepository;
import com.example.hotelbooking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Room> getAllRoom() {
        return roomRepository.findAll();
    }

    @Override
    public void add(Room room) {
        if(roomRepository.findByName(room.getName()).isPresent()){
            throw new RoomAlreadyExistsException("Room " + room.getName() + " already exists");
        }
        roomRepository.save(room);
    }

    @Override
    public void update(Room room) {
        Room oldUser = roomRepository.findById(room.getId()).orElseThrow(() -> new IllegalStateException("Room not found"));

        if (roomRepository.findByName(room.getName()).isPresent() && !room.getName().equals(oldUser.getName())) {
            throw new RoomAlreadyExistsException("Room " + room.getName() + " already exists");
        }
        roomRepository.save(room);
    }

    @Override
    public void delete(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new IllegalStateException("Not found " + id));
    }

    @Override
    public Room findByName(String name) {
        return roomRepository.findByName(name).orElseThrow(() -> new IllegalStateException("Room not found " + name));
    }


    @Override
    public List<Room> findAllActiveRooms() {
        return roomRepository.findAllActiveRooms();
    }

}
