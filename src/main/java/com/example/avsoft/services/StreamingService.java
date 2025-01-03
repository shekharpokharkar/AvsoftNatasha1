package com.example.avsoft.services;


import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
public class StreamingService {

    public Mono<Resource> getVideo(String batch,String title){
        Path videoPath = Paths.get("C:\\videos", "\\"+batch+"\\"+title+".mp4");
        Resource resource = new FileSystemResource(videoPath);
     return Mono.just(resource)   ;
    }
    
    
    public Mono<Resource> getCourseVideo(String batch,String title){
        Path videoPath = Paths.get("C:\\Users\\soura\\OneDrive\\Documents\\Avsoftdoc\\videos", "\\"+batch+"\\"+title+".mp4");
        Resource resource = new FileSystemResource(videoPath);
     return Mono.just(resource)   ;
    }
}