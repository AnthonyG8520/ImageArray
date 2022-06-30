package com.example.imagearray.models;

import javax.persistence.*;

@Entity
public class UsersFollowed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "followed_user")
    private User followedUser;

    public UsersFollowed(){}

    public UsersFollowed(long id, User user, User followedUser) {
        this.id = id;
        this.user = user;
        this.followedUser = followedUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFollowedUser() {
        return followedUser;
    }

    public void setFollowedUser(User followedUser) {
        this.followedUser = followedUser;
    }
}
