package com.example.avsoft.repositories;

import com.example.avsoft.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findDocumentByUrl(String url);
    void deleteDocumentByUrl(String url);
}
