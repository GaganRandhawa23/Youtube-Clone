package com.example.youtubeclone.youtubeclone.service;

import org.springframework.stereotype.Service;

@Service
public interface ChannelService {
    void saveChannel(String channelName);
}
