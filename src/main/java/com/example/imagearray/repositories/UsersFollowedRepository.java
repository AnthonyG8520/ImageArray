package com.example.imagearray.repositories;

import com.example.imagearray.models.User;
import com.example.imagearray.models.UsersFollowed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersFollowedRepository extends JpaRepository<UsersFollowed, Long> {
    UsersFollowed getByFollowedUserAndUser(User followedUser, User User);
}
