package com.example.imagearray.controllers;

import com.example.imagearray.repositories.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PostController {
    private final PostRepository postDao;

    public PostController(PostRepository postDao){
        this.postDao = postDao;
    }

    @GetMapping("/")
    public String index(){
        return "image/index";
    }

    @GetMapping("post/{id}")
    public String showPost(@PathVariable Long id, Model model){
        model.addAttribute("post", postDao.getById(id));
        return "post/individual";
    }
}
