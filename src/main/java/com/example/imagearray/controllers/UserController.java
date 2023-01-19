package com.example.imagearray.controllers;

import com.example.imagearray.models.Post;
import com.example.imagearray.models.User;
import com.example.imagearray.models.UsersFollowed;
import com.example.imagearray.repositories.PostRepository;
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
    private final PostRepository postDao;
    private final UsersFollowedRepository usersFollowedDao;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository userDao, PostRepository postDao, UsersFollowedRepository usersFollowedDao, PasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.postDao = postDao;
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
        System.out.println(usersFollowedDao.getFollowersCount(id));

        int followingCount = user.getUsersFollowed().size();
        model.addAttribute("followingCount", followingCount);

        int followerCount = usersFollowedDao.getFollowersCount(id);
        model.addAttribute("followerCount", followerCount);

        model.addAttribute("loggedUser", user);
        model.addAttribute("posts", postDao.getUsersPostsByTime(id));
        return "user/profile";
    }

    @GetMapping("/view-user/{id}")
    public String viewUser(@PathVariable Long id, Model model){
        User user = userDao.getById(id);
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        int followingCount = user.getUsersFollowed().size();
        model.addAttribute("followingCount", followingCount);

        int followerCount = usersFollowedDao.getFollowersCount(id);
        model.addAttribute("followerCount", followerCount);

        UsersFollowed checkIfFollowed = usersFollowedDao.getByFollowedUserAndUser(user, loggedUser);
        model.addAttribute("checkIfFollowed", checkIfFollowed);
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("user", user);
        model.addAttribute("posts", postDao.getUsersPostsByTime(id));
        return "user/view-user";
    }

    @PostMapping("/follow-user")
    public String followUser(@RequestParam Long followedUserId){
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User followedUser = userDao.getById(followedUserId);
        usersFollowedDao.save(new UsersFollowed(loggedUser, followedUser));
        return "redirect:/view-user/" + followedUserId;
    }

    @PostMapping("/unfollow-user")
    public String unfollowUser(@RequestParam Long followedUserId){
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userToUnfollow = userDao.getById(followedUserId);
        UsersFollowed followedUser = usersFollowedDao.getByFollowedUserAndUser(userToUnfollow, loggedUser);
        usersFollowedDao.delete(followedUser);
        return"redirect:/view-user/" + followedUserId;
    }
}