package com.example.youtubeclone.youtubeclone.controller;

import com.example.youtubeclone.youtubeclone.dto.ChannelViewDto;
import com.example.youtubeclone.youtubeclone.model.Channel;
import com.example.youtubeclone.youtubeclone.service.ChannelService;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @GetMapping("/channel/{channelName}")
    public String viewChannel(
            Model model,
            @PathVariable String channelName
    ) {
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

}