package com.example.avsoft.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.avsoft.entities.Courses;

public interface CourseRepository extends JpaRepository<Courses, Integer> {

	

}
