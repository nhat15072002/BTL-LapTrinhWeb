package com.example.hotelbooking.service.impl;

import com.example.hotelbooking.exception.ChangePasswordException;
import com.example.hotelbooking.exception.UserAlreadyExistsException;
import com.example.hotelbooking.model.Role;
import com.example.hotelbooking.model.User;
import com.example.hotelbooking.repository.UserRepository;
import com.example.hotelbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalStateException("Couldn't find user " + username));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalStateException("Couldn't find user " + id));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("Couldn't find user " + email));
    }

    @Override
    public void create(User user) throws UserAlreadyExistsException {
        User newUser = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(Role.USER)
                .isActive(true).build();
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User " + user.getUsername() + " already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email " + user.getEmail() + " already exists");
        }
        userRepository.save(newUser);
    }


    @Override
    public void changePassword(User user, String currentPassword, String newPassword, String confirmPassword) throws ChangePasswordException {
        String newPasswordEncoder = passwordEncoder.encode(newPassword);

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new ChangePasswordException("Incorrect current password");
        }
        if (!newPassword.equals(confirmPassword)) {
            throw new ChangePasswordException("Incorrect confirm password");
        }

        user.setPassword(newPasswordEncoder);
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        User oldUser = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalStateException("User not found"));

        if (userRepository.findByUsername(user.getUsername()).isPresent() && !user.getUsername().equals(oldUser.getUsername())) {
            throw new UserAlreadyExistsException("User " + user.getUsername() + " already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent() && !user.getEmail().equals(oldUser.getEmail())) {
            throw new UserAlreadyExistsException("Email " + user.getEmail() + " already exists");
        }
        userRepository.save(user);
    }


}
