package com.example.youtubeclone.youtubeclone.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.example.youtubeclone.youtubeclone.model.User;
import com.example.youtubeclone.youtubeclone.model.Video;
import com.example.youtubeclone.youtubeclone.repository.VideoRepository;
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

@Service
public class VideoService {

    @Value("${aws.bucketName}")
    private String BucketName;

    @Autowired
    private AmazonS3 s3Client;

    private VideoRepository videoRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public String uploadFile(MultipartFile file,String title, String description) {
        File convertedFile = convertMultiPartFile(file);
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(BucketName, filename, convertedFile));

        //demo user to set
        User user =new User();
        user.setUsername("gagan");
        user.setEmail("gagan@gmail.com");
        user.setPassword("1234");

        Video video = new Video();
        video.setDescription(description);
        video.setTitle(title);
        video.setUrl(filename);
        video.setCreatedAt(LocalDateTime.now());
        video.setLikeCount(0L);
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