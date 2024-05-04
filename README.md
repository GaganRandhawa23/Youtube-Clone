# YouTube Clone Project README

---

## Project Overview

This project is a YouTube clone built using Java Spring framework, PostgreSQL for the database, AWS S3 bucket for storing videos and other media files, and Google OAuth API for user authentication. The goal of this project is to replicate core functionalities of YouTube while adding some additional features to enhance user experience.

## Features

1. **User Authentication:**
   - Users can sign up and sign in using their Google accounts through Google OAuth API.

2. **Channel Management:**
   - Users can create, update, and delete their channels.
   - Channels can have profile pictures and descriptions.

3. **Video CRUD Operations:**
   - Users can upload, view, update, and delete videos.
   - Videos are stored in AWS S3 bucket for scalability and reliability.

4. **Likes and Comments:**
   - Users can like and comment on videos.
   - Like counts are displayed for each video.
   - Comments are threaded for better organization.

5. **Watch Later Playlist:**
   - Users can save videos to their "Watch Later" playlist for future viewing.

6. **Thumbnail Support:**
   - Thumbnails can be added for videos and displayed in video previews.

7. **Search, Sort, and Filters:**
   - Users can search for videos by title, description, or tags.
   - Videos can be sorted by relevance, date, or popularity.
   - Filters allow users to narrow down search results by category, upload date, or duration.

8. **Multithreading for Faster Uploads:**
   - Multithreading is implemented for uploading videos to AWS S3 bucket.
   - This improves upload speed and provides users with a faster and more reliable experience.

## Technologies Used

- Java Spring framework for backend development.
- PostgreSQL database for storing user data, video metadata, likes, and comments.
- AWS S3 bucket for storing videos and media files.
- Google OAuth API for user authentication.
- Thymeleaf and javascript for frontend development.

## Getting Started

1. **Clone:**
   - clone the repository to your local.
   - git clone <repository_url>.
     
2. **Set Up Environment:**
   - Set up your PostgreSQL database and configure the connection details in the application properties file.
   - Obtain AWS S3 credentials and configure them in the application properties file for storing videos.
   - Configure Google OAuth API credentials for user authentication.

3. **Build and Run:**
   - Build the project using Maven .
   - Run the application and access it via the specified port (default: 8080).

4. **Access the Application:**
   - Open your web browser and navigate to `http://localhost:8080`.
   - Sign up or sign in using your Google account.
   - Explore the various features of the YouTube clone.

## Acknowledgements

- Special thanks to the creators of Spring framework, PostgreSQL, AWS, Google OAuth API, and other libraries used in this project.
- Inspiration drawn from the original YouTube platform.

