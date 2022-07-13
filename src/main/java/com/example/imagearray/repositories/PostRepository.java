package com.example.imagearray.repositories;

import com.example.imagearray.models.Post;
import com.example.imagearray.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value= "SELECT * FROM post WHERE user_id IN (?) ORDER BY date DESC", nativeQuery = true)
    List<Post>getFollowedUserPostByTime(String ids);

    @Query(value = "SELECT * FROM post ORDER BY date DESC",nativeQuery = true)
    List<Post>findAllOrderByDate();
}
