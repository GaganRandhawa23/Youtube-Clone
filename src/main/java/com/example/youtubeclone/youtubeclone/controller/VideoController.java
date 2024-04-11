package com.example.youtubeclone.youtubeclone.controller;

import com.example.youtubeclone.youtubeclone.dto.VideoUploadDto;
import com.example.youtubeclone.youtubeclone.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/channel")
@AllArgsConstructor
public class VideoController {

    private VideoService videoService;

    @GetMapping("/upload")
    public String uploadVideo()
    {
        return "upload-video";
    }

    @PostMapping("/upload/v")
    public ResponseEntity<String> UploadVideo(
            VideoUploadDto videoUploadDto
    ){
        return new ResponseEntity<>(videoService.uploadFile(videoUploadDto), HttpStatus.OK);
//        return new ResponseEntity<>(videoService.uploadFile(file,title,description), HttpStatus.OK);
    }

    @GetMapping("/preview/{url}")
    public ResponseEntity<ByteArrayResource> previewFile(@PathVariable String url) {
        byte[] data = videoService.getVideo (url); // Assuming you have a method to retrieve video file data by URL
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .contentType(MediaType.parseMediaType("video/mp4")) // Set appropriate content type for video files
                .body(resource);
    }

    @GetMapping("/view/{url}")
    public String view(@PathVariable String url,Model  model)
    {
        model.addAttribute("url", url);
        return "view";
    }

    @GetMapping("/delete/{url}")
    public String delete(@PathVariable String url,Model  model)
    {
        videoService.deleteVideo(url);
        return "upload-video";
    }


}
