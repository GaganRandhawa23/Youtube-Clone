package com.example.youtubeclone.youtubeclone.service;


import com.example.youtubeclone.youtubeclone.model.Comment;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;

public interface CommentService {
    void saveComment(Comment comment);

    Comment findCommentByCommentId(Long id);

    void deleteCommentByCommentId(Long id);

    void addComment(String commentText, Long videoId, OAuth2AuthenticationToken authentication,Long commentId);
}
