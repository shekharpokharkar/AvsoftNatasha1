package com.example.avsoft.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.avsoft.dtos.VideoRequestDto;
import com.example.avsoft.entities.Video;
import com.example.avsoft.repositories.VideoRepository;
import com.example.avsoft.services.VideoUploadService;

import io.jsonwebtoken.io.IOException;

@RestController
public class VideoUploadController {

	@Autowired
	private VideoUploadService service;

	@Autowired
	private VideoRepository videoRepo;

	@PostMapping(value = "/upload-video", consumes = "multipart/form-data")
	public ResponseEntity<?> uploadVideo(@RequestParam("file") MultipartFile file, @ModelAttribute VideoRequestDto data)
			throws IOException, IllegalStateException, java.io.IOException, InterruptedException {

		if (file == null && file.isEmpty()) {
			throw new IllegalArgumentException("File cannot be empty");
		}

		String saveVideo = service.saveVideo(file, data);

		return ResponseEntity.ok(saveVideo);
	}
	/*
	 * @GetMapping("/get-video-list") public Map<String, List<String>>
	 * getVideoList() {
	 * 
	 * Map<String, List<String>> batchList = new HashMap<>(); List<Video> allVideo =
	 * videoRepo.findAll();
	 * 
	 * for (Video vid : allVideo) {
	 * 
	 * String batchName = vid.getBatch();
	 * 
	 * String title = vid.getTitle();
	 * 
	 * if (!batchList.containsKey(batchName)) { batchList.put(batchName, new
	 * ArrayList<>()); }
	 * 
	 * batchList.get(batchName).add(title);
	 * 
	 * }
	 * 
	 * return batchList;
	 * 
	 * }
	 */
	/*
	 * @GetMapping("/get-video/{id}") public Video getVideoList(@PathVariable int
	 * id) {
	 * 
	 * Optional<Video> byId = videoRepo.findById(id);
	 * 
	 * return byId.get();
	 * 
	 * }
	 */

	@GetMapping("/get-video/{batch}")
	public ResponseEntity<List<Video>> getVideoList(@PathVariable String batch) {

		List<Video> allVideoSortingByField = service.findAllVideoSortingByField(batch);

		return new ResponseEntity<List<Video>>(allVideoSortingByField, HttpStatus.OK);

	}

	@GetMapping("/get-video-page/{offset}/{pageSize}")
	public ResponseEntity<Page<Video>> getVideoListByPagination(@PathVariable("offset") int offset1,
			@PathVariable("pageSize") int pageSize1) {

		Page<Video> allVideoSortingByField = service.findAllVideoSortingByField(offset1, pageSize1);

		return new ResponseEntity<Page<Video>>(allVideoSortingByField, HttpStatus.OK);

	}

	@GetMapping("/get-video-list")
	public Map<String, List<Video>> getVideoList() {

		Map<String, List<Video>> batchList = new HashMap<>();

		List<Video> allVideo = videoRepo.findAll();

		for (Video vid : allVideo) {

			String batchName = vid.getBatch();
			// String title = vid.getTitle();

			if (!batchList.containsKey(batchName)) {
				batchList.put(batchName, new ArrayList<>());
			}
			batchList.get(batchName).add(vid);
		}
		return batchList;
	}

}
