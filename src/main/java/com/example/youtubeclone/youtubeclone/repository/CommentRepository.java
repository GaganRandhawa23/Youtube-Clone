package com.example.youtubeclone.youtubeclone.repository;

import com.example.youtubeclone.youtubeclone.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findCommentByCommentId(Long id);

    void deleteCommentByCommentId(Long id);


}
