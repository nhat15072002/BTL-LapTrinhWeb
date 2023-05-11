package com.example.hotelbooking.repository;

import com.example.hotelbooking.model.Room;
import com.example.hotelbooking.model.RoomState;
import com.example.hotelbooking.model.TypeOfRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
public interface RoomStateRepository extends JpaRepository<RoomState, Long> {
    @Query("SELECT DISTINCT r FROM room r " +
            "WHERE r.isActive = true " +
            "AND r NOT IN " +
            "(SELECT DISTINCT rs.room FROM room_state rs " +
            "WHERE (rs.date BETWEEN :startDate AND :endDate)) " +
            "AND r.type = :roomType" )
    List<Room> findAvailableRooms(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("roomType") TypeOfRoom roomType);

    RoomState findByRoomAndDate(Room room, LocalDate date);
}
