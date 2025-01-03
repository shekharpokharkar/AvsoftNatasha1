package com.example.avsoft.services;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.avsoft.entities.Blog;
import com.example.avsoft.entities.User;
import com.example.avsoft.repositories.BlogRepository;
import com.example.avsoft.repositories.UserRepository;

@Service
public class BlogServices {
    
    @Autowired
    private BlogRepository blogRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    
    public Blog createBlog(Blog blog) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();
            blog.setUseId(currentUser.getId());
            
            String slugTitle = toSlug(blog.getTitle());    
            blog.setTitle(slugTitle);
            return blogRepository.save(blog);
        } catch (Exception e) {
            throw new RuntimeException("Error creating blog: " + e.getMessage(), e);
        }
    }
    
    public List<Blog> getAllBlogs() {
    	 try {
             List<Blog> blogs = blogRepository.findByFlag(true);
             
             return blogs.stream().map(blog -> {
                 User author = userRepository.findById(blog.getUseId())
                                             .orElseThrow(() -> new RuntimeException("User not found with id: " + blog.getUseId()));
                 blog.setAuthorFullName(author.getFullName());
                 return blog;
             }).collect(Collectors.toList());
         } catch (Exception e) {
             throw new RuntimeException("Error fetching all blogs: " + e.getMessage(), e);
         }
    }
    
    public List<Blog> getUsersBlogs() {
        try {
            return blogRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching all blogs: " + e.getMessage(), e);
        }
    }
    

    public Boolean blogStatus(Boolean flag, int id) {
        try {
            Optional<Blog> blogOpt = blogRepository.findById(id);
            if (blogOpt.isPresent()) {
                Blog blog = blogOpt.get();
                blog.setFlag(flag);
                blogRepository.save(blog);
                return flag;
            } else {
                throw new IllegalArgumentException("Blog not found with id: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error changing blog status: " + e.getMessage(), e);
        }
    }

    
    public Blog getSingleBlog(String slug) {
        try {
            return blogRepository.findByTitle(slug);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching blog with slug: " + slug + ". " + e.getMessage(), e);
        }
    }
    
    public Optional<Blog> getBlogbyId(int id) {
        try {
        	
        	Optional<Blog> blog =  blogRepository.findById(id);
            return blog;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching blog with slug: " + ". " + e.getMessage(), e);
        }
    }
    
    
    public static String toSlug(String title) {
        String nowhitespace = WHITESPACE.matcher(title).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH)
                   .replaceAll("-{2,}", "-")
                   .replaceAll("^-|-$", "");
    }
    
    public Blog editBlog(String id, Blog updatedBlog) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();
            
            Blog existingBlog = blogRepository.findByTitle(id);
            
            if (existingBlog == null) {
                throw new IllegalArgumentException("Blog not found with id: " + id);
            }
            
            if (existingBlog.getUseId() != currentUser.getId()) {
                throw new SecurityException("User not authorized to edit this blog");
            }
            
            existingBlog.setTitle(toSlug(updatedBlog.getTitle()));
            existingBlog.setContent(updatedBlog.getContent());
            
            return blogRepository.save(existingBlog);
        } catch (Exception e) {
            throw new RuntimeException("Error editing blog: " + e.getMessage(), e);
        }
    }
    
    public List<Blog> getBlogsbyuserid() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();
            return blogRepository.findByUseId(currentUser.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching blogs for user: " + e.getMessage(), e);
        }
    }
}