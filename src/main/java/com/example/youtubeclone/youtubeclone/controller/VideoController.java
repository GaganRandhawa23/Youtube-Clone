package com.example.youtubeclone.youtubeclone.controller;

import com.example.youtubeclone.youtubeclone.dto.VideoUploadDto;
import com.example.youtubeclone.youtubeclone.model.Channel;
import com.example.youtubeclone.youtubeclone.model.Comment;
import com.example.youtubeclone.youtubeclone.model.User;
import com.example.youtubeclone.youtubeclone.model.Video;
import com.example.youtubeclone.youtubeclone.repository.VideoRepository;
import com.example.youtubeclone.youtubeclone.service.ChannelService;
import com.example.youtubeclone.youtubeclone.service.UserService;
import com.example.youtubeclone.youtubeclone.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@Controller
@RequestMapping("/channel")
public class VideoController {

    private final ChannelService channelService;
    private final VideoService videoService;
    private final UserService userService;

    @Autowired
    public VideoController(ChannelService channelService, VideoService videoService, UserService userService) {
        this.channelService = channelService;
        this.videoService = videoService;
        this.userService = userService;
    }

    @GetMapping("/upload")
    public String uploadVideo(
            OAuth2AuthenticationToken authentication,
            Model model
    )
    {
        OAuth2AuthenticatedPrincipal oauth2Principal = authentication.getPrincipal();
        String email = oauth2Principal.getAttribute("email");
        User user = userService.getUserByEmail(email);
        List<Channel> channelList = channelService.findChannelByUser(user);
        model.addAttribute("channels", channelList);
        return "upload-video";
    }

    @PostMapping("/upload/v")
    public ResponseEntity<String> UploadVideo(
            VideoUploadDto videoUploadDto
    ){
        return new ResponseEntity<>(videoService.uploadFile(videoUploadDto), HttpStatus.OK);
    }

    @GetMapping("/preview/{url}")
    public ResponseEntity<ByteArrayResource> previewFile(@PathVariable String url) {
        byte[] data = videoService.getVideo (url); // Assuming you have a method to retrieve video file data by URL
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .contentType(MediaType.parseMediaType("video/mp4")) // Set appropriate content type for video files
                .body(resource);
    }

    @GetMapping("/thumbnail/{url}")
    public ResponseEntity<ByteArrayResource> previewThumbnail(@PathVariable String url) {
        byte[] data = videoService.getThumbnailFromUrl (url);// Assuming you have a method to retrieve video file data by URL
        ByteArrayResource resource = new ByteArrayResource(data);
        String contentType = getContentType(url);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .contentType(MediaType.parseMediaType(contentType)) // Set appropriate content type for video files
                .body(resource);
    }

    private String getContentType(String url) {
        String extension = url.substring(url.lastIndexOf(".") + 1);
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            // Add more cases for other image types if needed
            default:
                return "application/octet-stream"; // Default to octet-stream if type is unknown
        }
    }

    @GetMapping("/view/{url}")
    public String view(
            @PathVariable String url,
            OAuth2AuthenticationToken authentication,
            Model  model)
    {
        OAuth2AuthenticatedPrincipal oauth2Principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
        String email = oauth2Principal.getAttribute("email");
        User existingUser = userService.getUserByEmail(email);
        Video video=videoService.findByUrl(url);
        List<Comment> comments = video.getComments();
        model.addAttribute("url", url);
        model.addAttribute("video",video);
        model.addAttribute("user",existingUser);
        model.addAttribute("comments", comments);
        return "view";
    }

    @GetMapping("/delete/{url}")
    public String delete(@PathVariable String url,Model  model)
    {
        videoService.deleteVideo(url);
        return "upload-video";
    }

    @PostMapping("/video/voting")
    public String feedbackActions(
            @RequestParam(name = "feedback") String feedback,
            @RequestParam(name = "videoId") Long videoId
    ) {
        Video video = videoService.findByVideoId(videoId);
            Long count = video.getLikeCount();
            if(feedback.equals("positive")) {
                count++;
                video.setLikeCount(count);
            }
            else {
                if(count > 0) {
                    count--;
                    video.setLikeCount(count);
                }
            }
        videoService.saveVideo(video);
        return "redirect:/channel/view/" + video.getUrl();
    }

}
