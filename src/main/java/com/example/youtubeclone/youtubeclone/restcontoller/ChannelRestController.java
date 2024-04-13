package com.example.youtubeclone.youtubeclone.restcontoller;

import com.example.youtubeclone.youtubeclone.service.ChannelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/channel")
@AllArgsConstructor
public class ChannelRestController {

    private final ChannelService channelService;

    @PostMapping("/create")
    public String createChannel(@RequestBody String channelName) {
        channelService.saveChannel(channelName);
        return "true";
    }
}
