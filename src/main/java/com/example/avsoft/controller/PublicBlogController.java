package com.example.avsoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.avsoft.entities.Blog;
import com.example.avsoft.services.BlogServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/public")
public class PublicBlogController {
	
	@Autowired
	BlogServices blogServices;
	
	
	@GetMapping("/blog/{slug}")
    public ResponseEntity<Blog> getBlog(@PathVariable String slug) {
        Blog blog = blogServices.getSingleBlog(slug);
        if (blog != null) {
            return ResponseEntity.ok(blog);
        } else {
            return ResponseEntity.notFound().build();  
        }
    }
	@GetMapping("/blogs")
	public ResponseEntity getBlogs() {
		 List <Blog> blog  = blogServices.getAllBlogs();
		 System.out.print(blog);
		 return ResponseEntity.ok(blog);
	}
	

}
