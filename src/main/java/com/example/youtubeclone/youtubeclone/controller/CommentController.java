package com.example.youtubeclone.youtubeclone.controller;


import com.example.youtubeclone.youtubeclone.model.Comment;
import com.example.youtubeclone.youtubeclone.model.User;
import com.example.youtubeclone.youtubeclone.model.Video;
import com.example.youtubeclone.youtubeclone.service.CommentService;
import com.example.youtubeclone.youtubeclone.service.UserService;
import com.example.youtubeclone.youtubeclone.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/videos")
public class CommentController {
    private final CommentService commentService;
    private final VideoService videoService;
    private final UserService userService;

    @Autowired
    public CommentController(CommentService commentService, VideoService videoService, UserService userService) {
        this.commentService = commentService;
        this.videoService = videoService;
        this.userService = userService;
    }

    @PostMapping("/addComment{videoId}")
    public String addComment(
            @RequestParam("theComment") String commentText,
            @PathVariable("videoId") Long videoId,
            @RequestParam(name="commentId" ,required = false) Long commentId,
            OAuth2AuthenticationToken authentication,
            Model model
    ) {
        commentService.addComment(commentText, videoId, authentication,commentId);
        Video video = videoService.findByVideoId(videoId);
        String url=video.getUrl();
        List<Comment> comments = video.getComments();
        model.addAttribute("comments", comments)
;        return "redirect:/channel/view/" + url;
    }

    @PostMapping("/deleteComment{comment_id}")
    public String deleteComment(@PathVariable("comment_id") Long id) {
        System.out.println(id);
        Comment comment = commentService.findCommentByCommentId(id);
        System.out.println(comment.getComment());
        Video video = comment.getVideo();
        commentService.deleteCommentByCommentId(id);
        String url=video.getUrl();
        return "redirect:/channel/view/" + url;
    }

    @GetMapping("/updateComment{videoId}/{commentId}")
    public String updateComment(
            @PathVariable("videoId") Long videoId,
            @PathVariable("commentId") Long commentId,
            Model model,
            OAuth2AuthenticationToken authentication
    ) {
        OAuth2AuthenticatedPrincipal oauth2Principal = authentication.getPrincipal();
        String email = oauth2Principal.getAttribute("email");
        User existingUser = userService.getUserByEmail(email);
        Video video = videoService.findByVideoId(videoId);
        List<Comment> comments = video.getComments();
        Comment comment = commentService.findCommentByCommentId(commentId);
        if (comment != null) {
            String theComment = comment.getComment();
            model.addAttribute("video", video);
            model.addAttribute("commentId", commentId);
            model.addAttribute("comments", comments);
            model.addAttribute("theComment", theComment);
            model.addAttribute("comment", comment);
            model.addAttribute("user",existingUser);
            commentService.saveComment(comment);
            return "view";
        } else {
            return "error";
        }
    }
}
