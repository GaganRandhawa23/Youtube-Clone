package com.example.youtubeclone.youtubeclone.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "username", updatable = false)
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "join_date")
    private LocalDateTime joinDate;
    @Column(name = "profile_dp")
    private String profilePic;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Comment> comments;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Video> uploadedVideos;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Video> watchLaterVideos;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Channel> subscribedChannels;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Channel> createdChannels;

}