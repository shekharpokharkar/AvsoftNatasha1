package com.example.avsoft.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders; // Correct import
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*")
public class SpringwebfluxVideoStreamingApplication {

    @Autowired
    private com.example.avsoft.services.StreamingService service;

    @GetMapping(value = "/video/{batch}/{title}", produces = "video/mp4")
    public Mono<Resource> getVideos( @RequestHeader(value = HttpHeaders.RANGE, required = false) String range,@RequestHeader HttpHeaders headers, @PathVariable String title, @PathVariable String batch) {
        // Print out all headers for debugging purposes
     

        return service.getVideo(batch, title);
    }
}
