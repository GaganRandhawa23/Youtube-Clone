package com.example.youtubeclone.youtubeclone.repository;

import com.example.youtubeclone.youtubeclone.model.Channel;
import com.example.youtubeclone.youtubeclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    Channel findChannelByChannelName(String channelName);
    List<Channel> findChannelsByUser(User user);
}
