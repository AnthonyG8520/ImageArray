package com.example.imagearray.repositories;

import com.example.imagearray.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
