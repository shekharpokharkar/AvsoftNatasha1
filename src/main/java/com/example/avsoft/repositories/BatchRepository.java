package com.example.avsoft.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.avsoft.entities.Batch;

public interface BatchRepository extends JpaRepository<Batch, Long> {
	 @Query("SELECT b FROM Batch b JOIN FETCH b.courses")
	    List<Batch> findAllWithCourses();
}
