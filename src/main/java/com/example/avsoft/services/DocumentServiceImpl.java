package com.example.avsoft.services;

import com.example.avsoft.entities.Document;
import com.example.avsoft.entities.User;
import com.example.avsoft.exceptions.DocumentServiceException;
import com.example.avsoft.repositories.DocumentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService {
    @Value("${document.upload-dir}")
    private String fileStorageLocation;

    @Autowired
    private DocumentRepository documentRepository;
    @Override
    @Transactional
    public String uploadDocument(MultipartFile file, String visibility) throws IOException {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String subDirectory = visibility.equals("public") ? "public" : "private";
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(fileStorageLocation, subDirectory, fileName);
        Files.createDirectories(filePath.getParent());
        Files.copy(file.getInputStream(), filePath);

        Document document = new Document();
        document.setFileName(fileName);
        document.setUrl("/" + subDirectory + "/" + fileName);
        document.setVisibility(visibility);
        document.setUser(currentUser);
        Document savedDocument = documentRepository.save(document);

        return savedDocument.getUrl();
    }

    @Override
    public Document getDocumentByUrl(String url) {
        Optional<Document> document= documentRepository.findDocumentByUrl(url);
        if(document.isEmpty())
            throw new DocumentServiceException("Document doesn't exist in db", HttpStatus.NOT_FOUND);
        return document.get();
    }

    @Override
    @Transactional
    public void deleteDocument(String url) throws IOException {

        Optional<Document> document = documentRepository.findDocumentByUrl(url);
        if(document.isEmpty()){
            throw new DocumentServiceException("Document does not exist in db", HttpStatus.NOT_FOUND );
        }
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!Objects.equals(currentUser.getId(), document.get().getUser().getId()) && !currentUser.getAuthorities().toString().contains("ROLE_ADMIN")){
            throw new DocumentServiceException("You are not authorized to delete this file", HttpStatus.UNAUTHORIZED);
        }

        documentRepository.deleteDocumentByUrl(url);
        Path filePath = Paths.get(fileStorageLocation+url);
        try{
            boolean isDeleted = Files.deleteIfExists(filePath);
            if(!isDeleted)
                throw new DocumentServiceException("file doesn't exist in system", HttpStatus.NOT_FOUND);
        }catch (Exception e){
            throw new DocumentServiceException("Document can't be deleted: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public String updateDocument(MultipartFile file, String currentUrl) throws IOException {
        deleteDocument(currentUrl);
        String visibility = currentUrl.startsWith("/public") ? "public" : "private";
        return uploadDocument(file, visibility);
    }

}
