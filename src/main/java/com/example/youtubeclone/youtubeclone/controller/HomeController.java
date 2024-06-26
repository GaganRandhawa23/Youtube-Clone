package com.example.youtubeclone.youtubeclone.controller;

import com.example.youtubeclone.youtubeclone.model.Channel;
import com.example.youtubeclone.youtubeclone.model.User;
import com.example.youtubeclone.youtubeclone.model.Video;
import com.example.youtubeclone.youtubeclone.service.ChannelService;
import com.example.youtubeclone.youtubeclone.service.UserService;
import com.example.youtubeclone.youtubeclone.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Controller
public class HomeController {
    private final VideoService videoService;
    private final UserService userService;
    private final ChannelService channelService;

    @Autowired
    public HomeController(VideoService videoService, UserService userService, ChannelService channelService) {
        this.videoService = videoService;
        this.userService = userService;
        this.channelService = channelService;
    }

    @GetMapping("/")
    public String home(Model model, OAuth2AuthenticationToken authentication) {
        OAuth2AuthenticatedPrincipal oauth2Principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
        String email = oauth2Principal.getAttribute("email");
        String name = oauth2Principal.getAttribute("name");
        String profile_dp = oauth2Principal.getAttribute("picture");
        User existingUser = userService.getUserByEmail(email);
        if (existingUser == null) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(VideoService.replaceWhiteSpaces(Objects.requireNonNull(name)));
            newUser.setProfilePic(profile_dp);
            newUser.setWatchLaterVideos(null);
            newUser.setJoinDate(LocalDateTime.now());
            userService.saveUser(newUser);
            Channel channel = Channel.builder()
                    .url("/channel/"+name)
                    .user(newUser)
                    .channelName(newUser.getUsername())
                    .profilePic(newUser.getProfilePic())
                    .createdAt(LocalDateTime.now())
                    .build();
            channelService.saveChannel(channel);
        }
        existingUser = userService.getUserByEmail(email);
        List<Video> videos = videoService.getAllVideos();
        model.addAttribute("videos", videos);
        model.addAttribute("user",existingUser);
        return "home";
    }
}
