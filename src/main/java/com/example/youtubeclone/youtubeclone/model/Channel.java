package com.example.youtubeclone.youtubeclone.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "channel")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id")
    private Long channelId;
    @Column(name = "channel_name")
    private String channelName;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "url")
    private String url;
    @Column(name = "profile_dp")
    private String profilePic;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "subscribedChannels", cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
    private List<User> subscribedUsers;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private List<Video> videos;
}
