package com.example.hotelbooking.controller;

import com.example.hotelbooking.exception.RoomAlreadyExistsException;
import com.example.hotelbooking.model.Room;
import com.example.hotelbooking.model.User;
import com.example.hotelbooking.service.RoomService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/rooms")
    public String show(Model model) {
        List<Room> rooms = roomService.findAllActiveRooms();
        model.addAttribute("rooms", rooms);
        return "rooms";
    }

    @GetMapping(value = "/admin/room")
    public String getAllRoom(Model model) {
        List<Room> roomList = roomService.getAllRoom();
        model.addAttribute("rooms", roomList);
        return "admin/list-room"; // sửa lại
    }

    @GetMapping(value = "/admin/room/add")
    public String addRoom(Model model) {
        model.addAttribute("room", new Room());
        return "admin/add-room";
    }

    // xử lý dữ liệu vừa nhập ở frontend và trả về 1 controller home
    @PostMapping(value = "/admin/room/add")
    public String processAddRoom(@Valid Room room, BindingResult bindingResult,
                                 @RequestParam("file") MultipartFile[] files,
                                 @RequestParam("active") String active,
                                 Model model) throws IOException {
        if(bindingResult.hasErrors()){
            return "admin/add-room";
        }

        List<String> img = new ArrayList<>();

        File saveFile = new ClassPathResource("static/uploads").getFile();
        for (MultipartFile file : files) {
            Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + UUID.randomUUID().toString() + "."
                    + FilenameUtils.getExtension(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            img.add("/static/uploads/" + path.getFileName().toString());
        }
        room.setImg(img);
        room.setActive(Boolean.valueOf(active));

        try{
            roomService.add(room);
        }catch (RoomAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/add-room";
        }
        return "redirect:/admin/room";
    }
    @GetMapping(value = "/admin/room/update/{id}")
    public String updateRoom(@PathVariable("id") Long id, Model model){
        Room room = roomService.findById(id);
        model.addAttribute("room", room);
        return "admin/update-room";
    }

    @PostMapping(value = "/admin/room/update")
    public String processUpdateRoom(@Valid Room room, BindingResult bindingResult,
                                    @RequestParam("file") MultipartFile[] files,
                                    @RequestParam("active") String active,
                                    Model model) throws IOException{
        if(bindingResult.hasErrors()){
            return "admin/update-room";
        }

        List<String> img = new ArrayList<>();

        File saveFile = new ClassPathResource("static/uploads").getFile();
        for (MultipartFile file : files) {
            Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + UUID.randomUUID().toString() + "."
                    + FilenameUtils.getExtension(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            img.add("/static/uploads/" + path.getFileName().toString());
        }
        room.setImg(img);
        room.setActive(Boolean.valueOf(active));

        try{
            roomService.update(room);
        }catch (RoomAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/update-room";
        }
        return "redirect:/admin/room";
    }

    @PostMapping("/admin/room/search-by-id")
    public String searchById(@RequestParam("id") Integer id,
                         Model model){
        Room room = null;
            try{
                room = roomService.findById(Long.valueOf(id));
            }catch (IllegalStateException e) {
                model.addAttribute("error",e.getMessage());
                return "admin/list-room";
            }

        model.addAttribute("room", room);
        return "admin/list-room";
    }

    @PostMapping("/admin/room/search-by-name")
    public String searchByName(@RequestParam("name") String name,
                         Model model){
        Room room = null;
        try{
            room = roomService.findByName(name.trim());
        }catch (IllegalStateException e) {
            model.addAttribute("error",e.getMessage());
            return "admin/list-room";
        }

        model.addAttribute("room", room);
        return "admin/list-room";
    }

}
