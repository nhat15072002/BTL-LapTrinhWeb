package com.example.hotelbooking.controller;

import com.example.hotelbooking.exception.ChangePasswordException;
import com.example.hotelbooking.exception.UserAlreadyExistsException;
import com.example.hotelbooking.model.User;
import com.example.hotelbooking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            userService.create(user);
        } catch (UserAlreadyExistsException e) {
            if (e.getMessage().contains("User")) {
                model.addAttribute("usernameError", e.getMessage());
            }
            if (e.getMessage().contains("Email")) {
                model.addAttribute("emailError", e.getMessage());
            }
            return "register";
        }
        return "redirect:/login";
    }

    @GetMapping("/admin/user")
    public String listUsers(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "admin/list-user";
    }

    @GetMapping("/admin/user/update/{id}")
    public String updateUser(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "admin/update-user";
    }

    @PostMapping("/admin/user/update")
    public String update(@Valid User user, BindingResult result,
                         @RequestParam("active") String active,
                         Model model) {
        if (result.hasErrors()) {
            return "admin/update-user";
        }

        user.setIsActive(Boolean.valueOf(active));
        try {
            userService.update(user);
        } catch (IllegalStateException e) {
            if (e.getMessage().contains("User")) {
                model.addAttribute("usernameError", e.getMessage());
            }
            if (e.getMessage().contains("Email")) {
                model.addAttribute("emailError", e.getMessage());
            }
            return "admin/update-user";
        }

        return "redirect:/admin/user";
    }

    @GetMapping("/account/profile")
    public String showProfile(Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        User thisUser = userService.findById(user.getId());

        model.addAttribute("user", thisUser);
        return "profile";
    }

    @PostMapping("/account/profile")
    public String updateProfile(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "profile";
        }
        userService.update(user);
        return "redirect:/account/profile";
    }

    @GetMapping("/account/change-password")
    public String changePassword() {
        return "change-password";
    }

    @PostMapping("/account/change-password")
    public String processChangePassword(@RequestParam("currentPassword") String currentPassword,
                                        @RequestParam("newPassword") String newPassword,
                                        @RequestParam("confirmPassword") String confirmPassword,
                                        Authentication authentication,
                                        Model model) {
        User user = (User) authentication.getPrincipal();
        try {
            userService.changePassword(user, currentPassword, newPassword, confirmPassword);
        } catch (ChangePasswordException e) {
            model.addAttribute("error", e.getMessage());
            return "change-password";
        }
        model.addAttribute("success", "Password changed successfully.");
        return "change-password";
    }

    @PostMapping("/admin/user/search-by-id")
    public String searchById(@RequestParam("id") Long id,
                         Model model) {
        User user = null;

        try {
            user = userService.findById(id);
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/list-user";
        }

        model.addAttribute("user", user);
        return "admin/list-user";
    }

    @PostMapping("/admin/user/search-by-username")
    public String searchByUsername(@RequestParam("username") String username,
                         Model model) {
        User user = null;

        try {
            user = userService.findByUsername(username);
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/list-user";
        }

        model.addAttribute("user", user);
        return "admin/list-user";
    }

    @PostMapping("/admin/user/search-by-email")
    public String searchByEmail(@RequestParam("email") String email,
                         Model model) {
        User user = null;

        try {
            user = userService.findByEmail(email);
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/list-user";
        }

        model.addAttribute("user", user);
        return "admin/list-user";
    }
}
