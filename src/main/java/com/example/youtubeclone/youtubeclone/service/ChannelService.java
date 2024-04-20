package com.example.youtubeclone.youtubeclone.service;

import com.example.youtubeclone.youtubeclone.model.Channel;
import com.example.youtubeclone.youtubeclone.model.User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChannelService {
    void saveChannelByName(String channelName, OAuth2AuthenticationToken authentication);

    Channel findByName(String channelName);

    void saveChannel(Channel channel);

    List<Channel> findChannelByUser(User user);

    void subscribeChannel(User loggedInUser, Long channelId);
}
