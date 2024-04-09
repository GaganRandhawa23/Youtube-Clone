package com.example.youtubeclone.youtubeclone.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;
    @Column(name = "comment")
    private String comment;
    @Column(name = "user")
    private User user;
    @Column(name = "video")
    private Video video;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
