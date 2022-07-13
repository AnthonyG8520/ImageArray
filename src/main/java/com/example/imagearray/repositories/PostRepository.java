package com.example.imagearray.repositories;

import com.example.imagearray.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value= "SELECT * FROM POST WHERE user_id IN (?1) ORDER BY date DESC", nativeQuery = true)
    List<Post>getFollowedUserPostByTime(String ids);
}
