package com.example.avsoft.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.avsoft.entities.Video;

public interface VideoRepository extends JpaRepository<Video, Integer> {

	@Query("SELECT v FROM Video v WHERE v.batch = :field ORDER BY v.createdAt ASC")
	public List<Video> findAll(String field);

	@Query("SELECT COUNT(v) FROM Video v WHERE v.batch = :batch AND v.title = :title")
	public int findByBatchAndTitle(String title, String batch);
}
