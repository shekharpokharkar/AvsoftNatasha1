package com.example.avsoft.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.avsoft.entities.Enquiry;

public interface EnquiryRepository extends JpaRepository<Enquiry, Integer>{

	@Query("SELECT e FROM Enquiry e WHERE e.createdAt >= :startDate")
    List<Enquiry> findAllByLast7Days(LocalDateTime startDate);

}
