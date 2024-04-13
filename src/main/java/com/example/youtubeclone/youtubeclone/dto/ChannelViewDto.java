package com.example.youtubeclone.youtubeclone.dto;

import com.example.youtubeclone.youtubeclone.model.User;
import com.example.youtubeclone.youtubeclone.model.Video;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ChannelViewDto {
    private String channelName;
    private LocalDateTime createdAt;
    private String url;
    private String profilePic;
    private User user;
    private Long numberOfSubscribedUsers;
    private Long numberOfVideos;
//    private List<User> subscribedUsers;
    private List<Video> videos;
}
