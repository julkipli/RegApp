package com.course.registration.service;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.registration.dao.repository.StudentRepository;
import com.course.registration.exception.StudentRegistrationException;
import com.course.registration.model.Course;
import com.course.registration.model.Student;



@Service
public class StudentService {
	
	private final StudentRepository studentRepository;
	private final CourseService courseService;

	@Autowired
	public StudentService(CourseService courseService, StudentRepository studentRepository) {
		this.courseService = courseService;
		this.studentRepository = studentRepository;
	}

	// add new student
	public Long addStudent(Student student) {
		student = studentRepository.save(student);
		return student.getStudentId();
	}

	// remove student by id
	public void removeStudent(Long studentId) {
		Optional<Student> student = studentRepository.findById(studentId);
		if (!student.isPresent()) {
			throw new StudentRegistrationException("Invalid StudentId :: " + studentId);
		}
		studentRepository.delete(student.get());
		
	}
	//add new student and his courses
	@Transactional
	public void addStudentToCourses(Student newStudent, Set<Course> courses) {
		
		long studentId = addStudent(newStudent);
		
		Optional<Student> studentOptional = studentRepository.findById(studentId);
		Student student = studentOptional.get();
		if (!studentOptional.isPresent()) {
			throw new StudentRegistrationException("Invalid Student id :: " + studentId);
		}
		
		student.setCourses(courses);
		studentRepository.save(student);
		
	}
		

	
	// get students by given course name and order by student name
	public Set<Student> getStudentsByCourseName(String courseName) {
		Optional<Course> course = courseService.getCourseByCourseName(courseName);
		if (!course.isPresent()) {
			throw new StudentRegistrationException("Invalid courseName :: " + courseName);
		}
		//for the sort
		Comparator<Student> studentByName = 
				(Student student1, Student student2) -> student1.getStudentName()
				.compareTo(student2.getStudentName());
		Set<Student> sortedStudents = new TreeSet<>(studentByName);

		Set<Student> students = course.get().getStudents();
		// since we dont need course details and it can be removed from the student collection
		students.forEach(student -> student.setCourses(null));
		//sort by student name
		sortedStudents.addAll(students);
		return sortedStudents;
	}


	
}
