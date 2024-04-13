package com.example.youtubeclone.youtubeclone.controller;

import com.example.youtubeclone.youtubeclone.model.Video;
import com.example.youtubeclone.youtubeclone.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {
    private final VideoService videoService;

    @GetMapping("/")
    public String home(Model model) {
        // Fetch all videos using the VideoService
        List<Video> videos = videoService.getAllVideos();
        System.out.println(videos);

        // Add the videos to the model to be rendered in the view
        model.addAttribute("videos", videos);

        // Return the name of the view to render (e.g., "home.html")
        return "home";
    }
}
