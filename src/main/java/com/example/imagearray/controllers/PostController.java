package com.example.imagearray.controllers;

import com.example.imagearray.models.Post;
import com.example.imagearray.models.User;
import com.example.imagearray.models.UsersFollowed;
import com.example.imagearray.repositories.PostRepository;
import com.example.imagearray.repositories.UserRepository;
import org.apache.tomcat.jni.Time;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
public class PostController {
    private final PostRepository postDao;
    private final UserRepository userDao;

    public PostController(PostRepository postDao, UserRepository userDao){
        this.postDao = postDao;
        this.userDao = userDao;
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("posts", postDao.findAll());
        return "post/index";
    }

    @GetMapping("/post/{id}")
    public String showPost(@PathVariable Long id, Model model){
        model.addAttribute("post", postDao.getById(id));
        return "post/individual";
    }

    @GetMapping("/post/create")
    public String createPost(Model model){
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("post", new Post());
        return "post/create";
    }

    @PostMapping("/post/create")
    public String savePost(@ModelAttribute Post post){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(user);
        post.setDate(LocalDateTime.now());
        System.out.println(post.getDate());
        postDao.save(post);
        return "redirect:/";
    }

    @GetMapping("/feed/{id}")
    public String myFeed(@PathVariable Long id, Model model){
        User user = userDao.getById(id);
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        String ids = "";
        for(UsersFollowed followed : user.getUsersFollowed()){
            ids += followed.getFollowedUser().getId() + ",";
        }
        model.addAttribute("followedUserPosts", postDao.getFollowedUserPostByTime(ids.substring(0, ids.length() -1)));
        System.out.println(ids.substring(0, ids.length() -1));
        return "post/my-feed";
    }
}
