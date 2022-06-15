package com.example.imagearray.controllers;

import com.example.imagearray.repositories.ImageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ImageController {
    private final ImageRepository imageDao;

    public ImageController(ImageRepository imageDao){
        this.imageDao = imageDao;
    }

    @GetMapping("/")
    public String index(){
        return "image/index";
    }

    @GetMapping("image/{id}/show")
    public String showPost(@PathVariable Long id, Model model){
        model.addAttribute("post", imageDao.getById(id));
        return "image/individual";
    }
}
