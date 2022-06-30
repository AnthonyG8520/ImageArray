package com.example.imagearray.controllers;

import com.example.imagearray.models.User;
import com.example.imagearray.models.UsersFollowed;
import com.example.imagearray.repositories.UserRepository;
import com.example.imagearray.repositories.UsersFollowedRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserRepository userDao;
    private final UsersFollowedRepository usersFollowedDao;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository userDao,UsersFollowedRepository usersFollowedDao ,PasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.usersFollowedDao = usersFollowedDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/sign-up")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        return "user/sign-up";
    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login";
    }

    @GetMapping("/profile/{id}")
    public String showProfile(@PathVariable Long id, Model model){
        User user = userDao.getById(id);
        model.addAttribute("user", user);
        return "user/profile";
    }
}
