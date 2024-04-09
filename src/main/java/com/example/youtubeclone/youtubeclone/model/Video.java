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
@Table(name = "video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long videoId;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "url")
    private String url;
    @Column(name = "channel")
    private Channel channel;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "like_count")
    private Long likeCount;

    private List<Comment> comments;

    private List<Tag> tags;
}
