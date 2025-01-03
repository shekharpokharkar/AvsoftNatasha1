package com.example.avsoft.services;

import com.example.avsoft.entities.Document;
import com.example.avsoft.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface DocumentService {
    /**
     * Stores a file based on the provided file visibility .
     *
     * @param file          the file to be stored
     * @param visibility the visibility type of the file, which can be either "public" or "private"
     * @return relative url of file where it's stored
     */
    String uploadDocument(MultipartFile file, String visibility) throws IOException;

    /**
     * @param url          file url
     * @return Document object containing info of that file
     */
    Document getDocumentByUrl(String url);

    /**
     * delete the file from location url
     * @param url          file url
     * @return void
     */
    void deleteDocument(String url) throws IOException;

    /**
     * replace the old file with new file
     * @param file          a multipart file
     * @return updated url of file location
     */
    String updateDocument(MultipartFile file, String currentUrl) throws IOException;
}
