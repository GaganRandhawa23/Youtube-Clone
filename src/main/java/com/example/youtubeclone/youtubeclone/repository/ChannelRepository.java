package com.example.youtubeclone.youtubeclone.repository;

import com.example.youtubeclone.youtubeclone.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

}
