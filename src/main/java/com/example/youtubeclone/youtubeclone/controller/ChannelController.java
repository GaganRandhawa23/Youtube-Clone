package com.example.youtubeclone.youtubeclone.controller;

import com.example.youtubeclone.youtubeclone.dto.ChannelViewDto;
import com.example.youtubeclone.youtubeclone.model.Channel;
import com.example.youtubeclone.youtubeclone.model.Comment;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ChannelController {

    private final ChannelService channelService;
    private final UserService userService;
    private final VideoService videoService;

    @Autowired
    public ChannelController(ChannelService channelService, UserService userService, VideoService videoService) {
        this.channelService = channelService;
        this.userService = userService;
        this.videoService = videoService;
    }

    @GetMapping("/channel/{channelName}")
    public String viewChannel(
            Model model,
            @PathVariable String channelName,
            OAuth2AuthenticationToken authentication
    ) {
        OAuth2AuthenticatedPrincipal oauth2Principal = authentication.getPrincipal();
        String email = oauth2Principal.getAttribute("email");
        User existingUser = userService.getUserByEmail(email);
        Channel channel = channelService.findByName(channelName);
        System.out.println(channel);
        ChannelViewDto channelViewDto = ChannelViewDto.builder()
                .numberOfSubscribedUsers((long) channel.getSubscribedUsers().size())
                .channelName(channelName)
                .user(channel.getUser())
                .url(channel.getUrl())
                .createdAt(channel.getCreatedAt())
                .numberOfVideos((long) channel.getVideos().size())
                .profilePic(channel.getProfilePic())
                .videos(channel.getVideos())
                .build();
        model.addAttribute("channel", channelViewDto);
        model.addAttribute("user",existingUser);
        return "view-channel";
    }

    @GetMapping("/channel/create")
    public String createChannelPage() {
        return "create-channel";
    }

    @PostMapping("/channel/c")
    public String createChannel(@RequestParam String channelName, OAuth2AuthenticationToken authentication) {
        channelService.saveChannelByName(channelName, authentication);
        return "redirect:/channel/" + channelName;
    }

    @PostMapping("/channel/subscribe")
    public String channelSubscribe(
            @RequestParam(name = "channelId") Long channelId,
            @RequestParam(name = "url") String url,
            OAuth2AuthenticationToken authentication,
            Model model
    ) {
        OAuth2AuthenticatedPrincipal oauth2Principal = authentication.getPrincipal();
        String email = oauth2Principal.getAttribute("email");
        User loggedInUser = userService.getUserByEmail(email);
        channelService.subscribeChannel(loggedInUser, channelId);
        model.addAttribute("url", url);
        return "redirect:/channel/view/" + url;
    }

    @GetMapping("/videos/watchlater")
    public String watchLater(
            OAuth2AuthenticationToken authentication,
            Model model
    ) {
        OAuth2AuthenticatedPrincipal oauth2Principal = authentication.getPrincipal();
        String email = oauth2Principal.getAttribute("email");
        User loggedInUser = userService.getUserByEmail(email);
        model.addAttribute("user", loggedInUser);
        List<Video> watchLaterVideos = loggedInUser.getWatchLaterVideos();
        model.addAttribute("watchLaterVideos", watchLaterVideos);
        return "watch-later";
    }

    @PostMapping("/channel/addtowatchlater")
    public String channelSubscribe(
            @RequestParam(name = "videoId") Long videoId,
            OAuth2AuthenticationToken authentication
    ) {
        OAuth2AuthenticatedPrincipal oauth2Principal = authentication.getPrincipal();
        String email = oauth2Principal.getAttribute("email");
        User loggedInUser = userService.getUserByEmail(email);
        Video video = videoService.findByVideoId(videoId);
        List<Video> existingWatchLaterVideos = loggedInUser.getWatchLaterVideos();
        if(!existingWatchLaterVideos.contains(video)) {
            existingWatchLaterVideos.add(video);
        }
        userService.saveUser(loggedInUser);
        return "redirect:/channel/view/" + video.getUrl();
    }

}