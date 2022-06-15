package com.example.imagearray.controllers;

import com.example.imagearray.repositories.UserRepository;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private final UserRepository userDao;

    public UserController(UserRepository userDao){
        this.userDao = userDao;
    }


}
