package com.example.imagearray.repositories;

import com.example.imagearray.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
