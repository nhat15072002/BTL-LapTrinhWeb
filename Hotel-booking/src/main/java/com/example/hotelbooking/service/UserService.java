package com.example.hotelbooking.service;

import com.example.hotelbooking.exception.ChangePasswordException;
import com.example.hotelbooking.exception.UserAlreadyExistsException;
import com.example.hotelbooking.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> findAllUsers();
    User findByUsername(String username);
    User findById(Long id);
    User findByEmail(String email);
    void create(User user) throws UserAlreadyExistsException;
    void changePassword(User user,String currentPassword, String newPassword, String confirmPassword) throws ChangePasswordException;
    void update(User user);
}
