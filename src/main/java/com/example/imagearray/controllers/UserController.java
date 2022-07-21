package com.example.imagearray.controllers;

import com.example.imagearray.models.User;
import com.example.imagearray.models.UsersFollowed;
import com.example.imagearray.repositories.UserRepository;
import com.example.imagearray.repositories.UsersFollowedRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login";
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

    @GetMapping("/profile/{id}")
    public String showProfile(@PathVariable Long id, Model model){
        User user = userDao.getById(id);
        model.addAttribute("loggedUser", user);
        model.addAttribute("posts", user.getPosts());
        return "user/profile";
    }

    @GetMapping("/view-user/{id}")
    public String viewUser(@PathVariable Long id, Model model){
        User user = userDao.getById(id);
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UsersFollowed checkIfFollowed = usersFollowedDao.getByFollowedUserAndUser(user, loggedUser);
        System.out.println(checkIfFollowed);
        model.addAttribute("checkIfFollowed", checkIfFollowed);
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("user", user);
        model.addAttribute("posts", user.getPosts());
        return "user/view-user";
    }

    @PostMapping("/follow-user")
    public String followUser(@RequestParam Long followedUserId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User followedUser = userDao.getById(followedUserId);
        usersFollowedDao.save(new UsersFollowed(user, followedUser));
        return "redirect:/profile/" + followedUser.getId();
    }

    @PostMapping("/unfollow-user")
    public String unfolloweUser(@RequestParam Long followedUserId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userToUnfollow = userDao.getById(followedUserId);
        UsersFollowed followedUser = usersFollowedDao.getByFollowedUserAndUser(userToUnfollow, user);
        usersFollowedDao.delete(followedUser);
        return"redirect:/profile/" + followedUserId;
    }
}
