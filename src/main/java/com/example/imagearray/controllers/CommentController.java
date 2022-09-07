package com.example.imagearray.controllers;

import com.example.imagearray.models.Comment;
import com.example.imagearray.models.Post;
import com.example.imagearray.models.User;
import com.example.imagearray.repositories.CommentRepository;
import com.example.imagearray.repositories.PostRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {
    private final CommentRepository commentDao;
    private final PostRepository postDao;

    public CommentController(CommentRepository commentDao, PostRepository postDao){
        this.commentDao = commentDao;
        this.postDao = postDao;
    }

    @PostMapping("/comment/create")
    public String saveComment(@RequestParam String text, @RequestParam Long postId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = postDao.getById(postId);
        Comment newComment = new Comment(text, post, user);
        commentDao.save(newComment);
        return "redirect:/post/" + postId;
    }

}
