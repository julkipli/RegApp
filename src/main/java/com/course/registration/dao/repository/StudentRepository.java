package com.course.registration.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.course.registration.model.Student;



public interface StudentRepository extends JpaRepository<Student, Long> {

}
