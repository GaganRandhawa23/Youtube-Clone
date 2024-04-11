package com.example.youtubeclone.youtubeclone.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.example.youtubeclone.youtubeclone.dto.VideoUploadDto;
import com.example.youtubeclone.youtubeclone.model.Tag;
import com.example.youtubeclone.youtubeclone.model.User;
import com.example.youtubeclone.youtubeclone.model.Video;
import com.example.youtubeclone.youtubeclone.repository.TagRepository;
import com.example.youtubeclone.youtubeclone.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {

    @Value("${aws.bucketName}")
    private String BucketName;

    @Autowired
    private AmazonS3 s3Client;

    private VideoRepository videoRepository;
    private TagRepository tagRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository, TagRepository tagRepository) {
        this.videoRepository = videoRepository;
        this.tagRepository = tagRepository;
    }


    public String uploadFile(VideoUploadDto videoUploadDto) {
        File convertedFile = convertMultiPartFile(videoUploadDto.getFile());
        String filename = System.currentTimeMillis() + "_" + videoUploadDto.getFile().getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(BucketName, filename, convertedFile));

        String[] tagNames = videoUploadDto.getTags().split(",");
        List<Tag> newTags = new ArrayList<>();
        for (String tagName : tagNames) {
            Tag tag = new Tag();
            tag.setTagName(tagName);
            tag.setCreatedAt(LocalDateTime.now());
            newTags.add(tag);
        }
        List<Tag> existingTags = tagRepository.findAll();
        List<Tag> allUniqueTagsForThisVideo = new ArrayList<>();
        for (Tag tag : newTags) {
            String tagName = tag.getTagName();
            boolean checkIfExists = false;
            for (Tag tempTag : existingTags) {
                String tempTagName = tempTag.getTagName();
                if (tempTagName.equals(tagName)) {
                    checkIfExists = true;
                    allUniqueTagsForThisVideo.add(tempTag);
                    break;
                }
            }
            if (!checkIfExists) {
                allUniqueTagsForThisVideo.add(tag);
            }
        }

        Video video = Video.builder()
                .title(videoUploadDto.getTitle())
                .description(videoUploadDto.getDescription())
                .url(filename)
                .createdAt(LocalDateTime.now())
                .likeCount(0L)
                .user(null)
                .tags(allUniqueTagsForThisVideo)
                .build();
        videoRepository.save(video);
        convertedFile.delete();
        return "file Uploaded : " + filename;
    }

    public byte[] getVideo(String url) {
        try {
            S3Object s3Object = s3Client.getObject(BucketName, url);
            S3ObjectInputStream inputStream = s3Object.getObjectContent();
            byte[] content = StreamUtils.copyToByteArray(inputStream);
            inputStream.close();
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    public String deleteVideo(String url) {
        // Delete from Amazon S3
        s3Client.deleteObject(BucketName, url);

        // Delete from database
        videoRepository.deleteByUrl(url);

        return url + " removed from Amazon S3 and database.";
    }


    private File convertMultiPartFile(MultipartFile file) {
        File convertedFileEntity = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFileEntity)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            System.out.println("error converting file " + e);
        }
        return convertedFileEntity;
    }
}