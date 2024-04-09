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
@Table(name = "channel")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id")
    private Long channelId;
    @Column(name = "channel_name")
    private String channelName;
    @Column(name = "user")
    private User user;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "url")
    private String url;
    @Column(name = "profile_dp")
    private String profilePic;

    private List<Video> uploadedVideos;
}
