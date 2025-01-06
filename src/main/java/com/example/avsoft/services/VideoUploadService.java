package com.example.avsoft.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.avsoft.dtos.VideoRequestDto;
import com.example.avsoft.entities.Video;
import com.example.avsoft.exceptions.VideoAlreadyExist;
import com.example.avsoft.repositories.VideoRepository;

import io.jsonwebtoken.io.IOException;

@Service
public class VideoUploadService {

	@Autowired
	private VideoRepository videoRepo;

	@Value("${file.upload-video}")
	private String videoPath;

	@Transactional
	// @Async
	public String saveVideo(MultipartFile file, VideoRequestDto data) throws IOException, InterruptedException {

		Video videoNew = new Video();
		videoNew.setTitle(data.getTitle()); // Save the title from DTO
		videoNew.setBatch(data.getBatch()); // Save the batch name
		videoNew.setVideostatus(false);
		Video savedVideo = videoRepo.save(videoNew);

		String originalFilename = file.getOriginalFilename();

		String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

		// check the file extension if otherthan mp4 present then throw the exception

		/*
		 * if (!(".mp4").equals(extension)) { throw new
		 * RuntimeException("File should be only .mp4"); }
		 */

		if (file != null && !file.isEmpty()) {
			Path directoryPath = Paths.get(videoPath, data.getBatch());

			if (!Files.exists(directoryPath)) {
				try {
					Files.createDirectories(directoryPath);
				} catch (java.io.IOException e) {
					e.printStackTrace();
					throw new IllegalStateException("Failed to create directories for batch", e);
				}
			}

			// Set the file name to the title from VideoRequestDto with its extension
			String titleFilename = data.getTitle() + extension;

			// check if same video is already uploaded
			int checkIfFileAlreadyExist = checkIfFileAlreadyExist(data.getTitle(), data.getBatch());

			if (checkIfFileAlreadyExist > 1) {
				throw new VideoAlreadyExist("Given Video Already Exist in dataBase");
			}

			// Save the file to the batch folder with the title as the filename Path
			Path filepath = null;
			try {

				filepath = Paths.get(directoryPath + "/" + titleFilename);
				Instant startTime = Instant.now();
				Files.write(filepath, file.getBytes()); // Save the file
				Instant endTime = Instant.now();
				savedVideo.setVideostatus(true);
				long durationMillis = java.time.Duration.between(startTime, endTime).toMillis();

				// Calculate the file size in bytes
				long fileSize = file.getSize(); // Size in bytes

				// Optionally, convert file size to a readable format (e.g., MB)
				double fileSizeInMB = fileSize / (1024.0 * 1024.0); // Convert bytes to MB

				// Return success message with the upload time and file size
				return String.format("Video uploaded successfully in %d milliseconds. File size: %.2f MB.",
						durationMillis, fileSizeInMB);

				
			} catch (java.io.IOException e) {
				e.printStackTrace();
				// throw new IllegalStateException("Failed to save the video file", e);
				return "File upload failed: " + e.getMessage();
			}

		} else {
			throw new IllegalArgumentException("File cannot be empty");

		}

	}

	// Check file exist or not
	private int checkIfFileAlreadyExist(String title, String batch) {

		return videoRepo.findByBatchAndTitle(title, batch);

	}

	private boolean verifyFileUpload(Path path) {
		// Check if file exists and is a valid file
		File uploadedFile = path.toFile();
		return uploadedFile.exists() && uploadedFile.isFile() && uploadedFile.length() > 0;
	}

	public List<Video> findAllVideoSortingByField(String field) {

		List<Video> videoList = videoRepo.findAll(field);

		return videoList;

	}

	public Page<Video> findAllVideoSortingByField(int offset1, int pageSize1) {

		Page<Video> videos = videoRepo.findAll(PageRequest.of(offset1, pageSize1));

		return videos;

	}

}
