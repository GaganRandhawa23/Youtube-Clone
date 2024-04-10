package com.example.youtubeclone.youtubeclone.service;

import com.example.youtubeclone.youtubeclone.model.Channel;
import com.example.youtubeclone.youtubeclone.repository.ChannelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class ChannelManager implements ChannelService{

    private final ChannelRepository channelRepository;

    @Override
    public void saveChannel(String channelName) {
        Channel channel = Channel.builder()
                .url("/channel/" + channelName)
                .user(null)
                .channelName(channelName)
                .createdAt(LocalDateTime.now())
                .videos(null)
                .subscribedUsers(null)
                .profilePic(null)
                .build();
        channelRepository.save(channel);
    }
}
