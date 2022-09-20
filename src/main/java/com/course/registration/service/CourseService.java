package com.course.registration.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.registration.dao.repository.CourseRepository;
import com.course.registration.model.Course;



@Service
public class CourseService {

	private final CourseRepository courseRepository;

	@Autowired
	public CourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	// add new course
	public Long addCourse(Course course) {
		course = courseRepository.save(course);
		return course.getCourseId();
	}

	// get courses by course name
	public Optional<Course> getCourseByCourseName(String courseName) {
		return courseRepository.findCourseByCourseName(courseName);
	}


	
	
	
	
}
