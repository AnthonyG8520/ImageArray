package com.example.imagearray.controllers;

import com.example.imagearray.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {
    private final UserRepository userDao;

    public UserController(UserRepository userDao){
        this.userDao = userDao;
    }

    @GetMapping("user/{id}")
    public String showProfile(@PathVariable Long id, Model model){
        model.addAttribute("user", userDao.getById(id));
        return "user/profile";
    }
}
