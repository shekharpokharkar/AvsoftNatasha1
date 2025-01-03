package com.example.avsoft.configs;

import com.example.avsoft.entities.Document;
import com.example.avsoft.entities.User;
import com.example.avsoft.services.DocumentService;
import com.example.avsoft.services.UserService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class DocumentAccessFilter extends OncePerRequestFilter {

    @Autowired
    private DocumentService documentService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (!requestURI.startsWith("/private/")) {
            filterChain.doFilter(request, response);
            return;
        }

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(currentUser.getAuthorities().toString().contains("ROLE_ADMIN")){
            filterChain.doFilter(request,response);
            return;
        }

        Document document = documentService.getDocumentByUrl(requestURI);
        if(!(Objects.equals(document.getUser().getId(), currentUser.getId()))){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }


        filterChain.doFilter(request, response);
    }
}

