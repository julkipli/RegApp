package com.course.registration.exception;

public class StudentRegistrationException extends IllegalStateException {
	private static final long serialVersionUID = 1L;
	
	public StudentRegistrationException(String message) {
		super(message);
	}
	
	public StudentRegistrationException(Throwable e) {
		super(e);
	}

}
