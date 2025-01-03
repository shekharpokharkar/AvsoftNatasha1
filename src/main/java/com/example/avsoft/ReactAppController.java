package com.example.avsoft;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;

@Controller
public class ReactAppController {

    @RequestMapping("/")
    public String serveApp(HttpServletRequest request) throws IOException {
        // Check the domain (host) of the incoming request
        String host = request.getHeader("Host");

        if ("avsofttechnologies.com".equals(host)) {
            // Serve React App 1 (from /static/abc)
            return "forward:/index.html";  // Forward to the index.html of App 1
        } else if ("agastyalibrary.com".equals(host)) {
            // Serve React App 2 (from /static/xyz)
        	return "forward:/aklakshay/index.html";  // Forward to the index.html of App 2
        } 
        else if ("eklakshyalibrary.com".equals(host)) {
            // Serve React App 2 (from /static/xyz)
            return "forward:/aklakshay/index.html";  // Forward to the index.html of App 2
        } 
        
        else {
            return "error";  // Or handle the default case
        }
    }
}
