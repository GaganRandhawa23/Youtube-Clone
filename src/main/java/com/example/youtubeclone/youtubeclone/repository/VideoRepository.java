package com.example.youtubeclone.youtubeclone.repository;

import com.example.youtubeclone.youtubeclone.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository  extends JpaRepository<Video,Integer>{

    void deleteByUrl(String url);

    Video findByVideoId(Long id);


    Video findVideoByUrl(String url);
}
