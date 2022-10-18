package com.cst438.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.cst438.domain.Course;
import com.cst438.domain.CourseDTOG;
import com.cst438.domain.CourseRepository;

public class RegistrationServiceREST extends RegistrationService {

	@Autowired
	CourseRepository courseRepository;
	
	RestTemplate restTemplate = new RestTemplate();
	
	@Value("${registration.url}") 
	String registration_url;
	
	public RegistrationServiceREST() {
		System.out.println("REST registration service ");
	}
	
	@Override
	public void sendFinalGrades(int course_id , CourseDTOG courseDTO) { 
		
		//TODO  complete this method in homework 4
		System.out.println(course_id);
		System.out.println("Sending final grades for " + course_id);
		restTemplate.put(registration_url+"/course/"+course_id, courseDTO);
		System.out.println("Final grades sent");

	}
}
