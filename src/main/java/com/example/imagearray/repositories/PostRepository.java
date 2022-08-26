package com.example.imagearray.repositories;

import com.example.imagearray.models.Post;
import com.example.imagearray.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value= "SELECT * FROM post WHERE user_id IN :ids ORDER BY date DESC", nativeQuery = true)
    List<Post>getFollowedUserPostByTime(@Param("ids") List<Long> ids);

    @Query(value = "SELECT * FROM post ORDER BY date DESC",nativeQuery = true)
    List<Post>findAllOrderByDate();

    @Query(value = "SELECT * FROM post WHERE user_id = ? ORDER BY date DESC", nativeQuery = true)
    List<Post>getUsersPostsByTime(Long id);
}
