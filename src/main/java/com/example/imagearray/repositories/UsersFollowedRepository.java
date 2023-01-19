package com.example.imagearray.repositories;

import com.example.imagearray.models.User;
import com.example.imagearray.models.UsersFollowed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersFollowedRepository extends JpaRepository<UsersFollowed, Long> {
    UsersFollowed getByFollowedUserAndUser(User followedUser, User User);

    @Query(value = "SELECT COUNT(*) FROM users_followed WHERE followed_user = ?", nativeQuery = true)
    int getFollowersCount(long userId);
}
