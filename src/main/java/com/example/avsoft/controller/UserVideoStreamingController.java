package com.example.avsoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RequestMapping("/stream")
@RestController
public class UserVideoStreamingController {
	    @Autowired
	    private com.example.avsoft.services.StreamingService service;

	    @GetMapping(value = "/{batch}/{title}", produces = "video/mp4")
	    public Mono<Resource> getVideos(@RequestHeader(value = HttpHeaders.RANGE, required = false) String range,@RequestHeader HttpHeaders headers, @PathVariable String title, @PathVariable String batch) {
	        return service.getCourseVideo(batch, title);
	    }
	    
	   
	    @GetMapping("/test")
	    public String test() {
	    	return "Hello";
	    }

}
