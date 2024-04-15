package com.example.youtubeclone.youtubeclone.service;

import com.example.youtubeclone.youtubeclone.model.Comment;
import com.example.youtubeclone.youtubeclone.model.User;
import com.example.youtubeclone.youtubeclone.model.Video;
import com.example.youtubeclone.youtubeclone.repository.CommentRepository;
import com.example.youtubeclone.youtubeclone.repository.UserRepository;
import com.example.youtubeclone.youtubeclone.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentManager implements CommentService {
    private CommentRepository commentRepository;
    private VideoRepository videoRepository;
    private UserRepository userRepository;

    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public Comment findCommentByCommentId(Long id) {
        return commentRepository.findCommentByCommentId(id);
    }

    @Override
    @Transactional
    public void deleteCommentByCommentId(Long id) {
        commentRepository.deleteCommentByCommentId(id);
    }

    @Override
    public void addComment(String commentText, Long videoId, OAuth2AuthenticationToken authentication,Long commentId) {
        OAuth2AuthenticatedPrincipal oauth2Principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
        String email = oauth2Principal.getAttribute("email");
        User loggedInUser = userRepository.findUserByEmail(email);
        Video video = videoRepository.findByVideoId(videoId);
        List<Comment> listOfComments = video.getComments();
        Comment comment;
        if (commentId == null) {
            comment = Comment.builder()
                    .comment(commentText)
                    .likeCount(0L)
                    .createdAt(LocalDateTime.now())
                    .video(video)
                    .user(loggedInUser)
                    .build();
        } else {
            comment = commentRepository.findCommentByCommentId(commentId);
            comment.setComment(commentText);
        }
        listOfComments.add(comment);
        video.setComments(listOfComments);
        commentRepository.save(comment);
        videoRepository.save(video);
    }
}
