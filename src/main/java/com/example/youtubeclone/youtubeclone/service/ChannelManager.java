package com.example.youtubeclone.youtubeclone.service;

import com.example.youtubeclone.youtubeclone.model.Channel;
import com.example.youtubeclone.youtubeclone.model.User;
import com.example.youtubeclone.youtubeclone.repository.ChannelRepository;
import com.example.youtubeclone.youtubeclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class ChannelManager implements ChannelService{

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    @Override
    public void saveChannelByName(String channelName, OAuth2AuthenticationToken authentication) {
        OAuth2AuthenticatedPrincipal oauth2Principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();
        String email = oauth2Principal.getAttribute("email");
        String name = oauth2Principal.getAttribute("name");
        String profileDp = oauth2Principal.getAttribute("picture");
        User existingUser = userRepository.findUserByEmail(email);
        if(existingUser == null) {
            User user = User.builder()
                    .email(email)
                    .profilePic(profileDp)
                    .joinDate(LocalDateTime.now())
                    .username(name)
                    .build();
            Channel channel = Channel.builder()
                    .url("/channel/" + channelName)
                    .user(user)
                    .channelName(channelName)
                    .createdAt(LocalDateTime.now())
                    .profilePic(profileDp)
                    .build();
            channelRepository.save(channel);
        }
        else {
            Channel channel = Channel.builder()
                    .url("/channel/" + channelName)
                    .user(existingUser)
                    .channelName(channelName)
                    .createdAt(LocalDateTime.now())
                    .profilePic(profileDp)
                    .build();
            channelRepository.save(channel);
        }
    }

    @Override
    public Channel findByName(String channelName) {
        return channelRepository.findChannelByChannelName(channelName);
    }

    @Override
    public void saveChannel(Channel channel) {
        channelRepository.save(channel);
    }

    @Override
    public List<Channel> findChannelByUser(User user) {
        return channelRepository.findChannelsByUser(user);
    }
}
