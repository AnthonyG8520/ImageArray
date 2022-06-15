package com.example.imagearray.controllers;

import com.example.imagearray.repositories.CommentRepository;
import org.springframework.stereotype.Controller;

@Controller
public class CommentController {
    private final CommentRepository commentDao;

    public CommentController(CommentRepository commentDao){
        this.commentDao = commentDao;
    }


}
