package com.example.imagearray.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @Column
    private String profilePicture;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<UsersFollowed> usersFollowed;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Comment> comments;

    public User(){}

    public User(User copy){
        id = copy.id;
        username = copy.username;
        password = copy.password;
        email = copy.email;
        posts = copy.posts;
        comments = copy.comments;
    }

    public User(long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<UsersFollowed> getUsersFollowed() {
        return usersFollowed;
    }

    public void setUsersFollowed(List<UsersFollowed> usersFollowed) {
        this.usersFollowed = usersFollowed;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Post> getAllFollowedUsersPosts(User user){
        List<Post> followedposts = null;
        for(UsersFollowed followed : user.getUsersFollowed()){
            followedposts = followed.getFollowedUser().getPosts();
        }
        followedposts.sort();
        return followedposts;
    }
}
