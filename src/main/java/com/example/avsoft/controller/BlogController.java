package com.example.avsoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.avsoft.entities.Blog;
import com.example.avsoft.services.BlogServices;

@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/blog")
@RestController
public class BlogController {
	
	 @Autowired
     private BlogServices blogServices;
	
	 @PostMapping("/create")
	 public ResponseEntity createBlog(@RequestBody Blog blog)
	 {
		 Blog newBlog =  blogServices.createBlog(blog);
		 return ResponseEntity.ok("Blog " + newBlog.getTitle() + "Created Sucessfully");
	 }
	 
	 @GetMapping("")
	 public ResponseEntity getallArticles() {	   
		 List <Blog> blog  = blogServices.getBlogsbyuserid();
		 return ResponseEntity.ok(blog);
		 
	 }
	 
	 @PostMapping("/edit/{id}")
	 public ResponseEntity editArticle(@PathVariable String id,@RequestBody Blog blog) {
		 Blog newBlog =  blogServices.editBlog(id,blog);
		 return ResponseEntity.ok(blog);
		 
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 

}