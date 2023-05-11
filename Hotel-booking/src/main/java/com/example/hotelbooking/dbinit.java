package com.example.hotelbooking;

import com.example.hotelbooking.model.Room;
import com.example.hotelbooking.model.TypeOfRoom;
import com.example.hotelbooking.model.User;
import com.example.hotelbooking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class dbinit implements CommandLineRunner {
    @Autowired
    private RoomService roomService;

    @Override
    public void run(String... args) throws Exception {
//        addRoom();
    }

    void addRoom() {
        List<String> imgs = new ArrayList<>();
        imgs.add("/static/images/room-1.jpg");
        imgs.add("/static/images/room-2.jpg");
        imgs.add("/static/images/room-3.jpg");
        Room room = Room.builder()
                .name("101")
                .type(TypeOfRoom.normal1)
                .costPerNight(BigDecimal.valueOf(100))
                .description("When she reached the first hills of the Italic Mountains, she had a last view back on the " +
                        "skyline of her hometown Bookmarksgrove, the headline of Alphabet Village and the subline of her" +
                        " own road, the Line Lane. Pityful a rethoric question ran over her cheek, then she continued her way.")
                .img(imgs)
                .video("https://vimeo.com/45830194")
                .size(45)
                .build();
        roomService.add(room);

        imgs = new ArrayList<>();
        imgs.add("/static/images/room-1.jpg");
        imgs.add("/static/images/room-2.jpg");
        imgs.add("/static/images/room-3.jpg");
        room = Room.builder()
                .name("101")
                .type(TypeOfRoom.normal2)
                .costPerNight(BigDecimal.valueOf(100))
                .description("When she reached the first hills of the Italic Mountains, she had a last view back on the " +
                        "skyline of her hometown Bookmarksgrove, the headline of Alphabet Village and the subline of her" +
                        " own road, the Line Lane. Pityful a rethoric question ran over her cheek, then she continued her way.")
                .img(imgs)
                .video("https://vimeo.com/45830194")
                .size(45)
                .build();
        roomService.add(room);

        imgs = new ArrayList<>();
        imgs.add("/static/images/room-1.jpg");
        imgs.add("/static/images/room-2.jpg");
        imgs.add("/static/images/room-3.jpg");
        room = Room.builder()
                .name("101")
                .type(TypeOfRoom.vip1)
                .costPerNight(BigDecimal.valueOf(100))
                .description("When she reached the first hills of the Italic Mountains, she had a last view back on the " +
                        "skyline of her hometown Bookmarksgrove, the headline of Alphabet Village and the subline of her" +
                        " own road, the Line Lane. Pityful a rethoric question ran over her cheek, then she continued her way.")
                .img(imgs)
                .video("https://vimeo.com/45830194")
                .size(45)
                .build();
        roomService.add(room);

        imgs = new ArrayList<>();
        imgs.add("/static/images/room-1.jpg");
        imgs.add("/static/images/room-2.jpg");
        imgs.add("/static/images/room-3.jpg");
        room = Room.builder()
                .name("101")
                .type(TypeOfRoom.vip2)
                .costPerNight(BigDecimal.valueOf(100))
                .description("When she reached the first hills of the Italic Mountains, she had a last view back on the " +
                        "skyline of her hometown Bookmarksgrove, the headline of Alphabet Village and the subline of her" +
                        " own road, the Line Lane. Pityful a rethoric question ran over her cheek, then she continued her way.")
                .img(imgs)
                .video("https://vimeo.com/45830194")
                .size(45)
                .build();
        roomService.add(room);
    }
}
