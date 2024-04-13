package com.example.youtubeclone.youtubeclone.dto;

import com.example.youtubeclone.youtubeclone.model.Tag;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class VideoUploadDto {
    private String title;
    private String description;
    private MultipartFile file;
    private String tags;
    private String channelName;
}
