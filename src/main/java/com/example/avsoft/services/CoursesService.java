package com.example.avsoft.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.avsoft.entities.Courses;
import com.example.avsoft.repositories.CourseRepository;

@Service
public class CoursesService {

	@Autowired
	private CourseRepository courseRepository;
	
	public Courses saveCourse(Courses courses) {	
		Courses newCourse = courseRepository.save(courses);
		return newCourse;	
	}
	
	public List<Courses> getCourses() {
		List<Courses> courses = courseRepository.findAll();
		
		
        return courses;
    }

	
	
	
}
