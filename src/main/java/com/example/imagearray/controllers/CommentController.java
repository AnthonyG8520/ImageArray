package com.example.imagearray.controllers;

import com.example.imagearray.models.Comment;
import com.example.imagearray.repositories.CommentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    private final CommentRepository commentDao;

    public CommentController(CommentRepository commentDao){
        this.commentDao = commentDao;
    }

    @PostMapping("/comment/create")
    public String saveComment(@ModelAttribute Comment comment){


        commentDao.save(comment);
        return "redirect:/post/" + postid;
    }

}
